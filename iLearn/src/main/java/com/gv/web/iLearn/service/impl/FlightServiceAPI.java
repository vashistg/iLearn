package  com.gv.web.iLearn.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gv.web.iLearn.commands.hystrix.AirlineCommand;
import com.gv.web.iLearn.commands.hystrix.GoAir;
import com.gv.web.iLearn.commands.hystrix.IndigoCommand;
import com.gv.web.iLearn.commands.hystrix.MPTBCommand;
import com.gv.web.iLearn.commands.hystrix.SpiceCommand;
import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.pojo.SearchResponse;
import com.gv.web.iLearn.service.FlightService;

import rx.Observable;
import rx.Subscriber;

/**
 * Date :23/11/2016
 * @author GauravV
 *
 */
public class FlightServiceAPI implements FlightService {

	@Override
	public SearchResponse searchFlights(SearchRequest request) {
		SearchResponse response = new SearchResponse();
		insertDelay(request);
		List<AirlineCommand> alCommands = getAirLineCommands();
		int i=1;
		for(AirlineCommand alCommand : alCommands){
			response.addAll(alCommand.execute());
		}
		return response;
	}
	
	@Override
	public Observable<SearchResponse> searchRxFlights() {
		final ObjectMapper objectMapper = new ObjectMapper(); 
		final SearchResponse response = new SearchResponse();
		WebSocketClient transport = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new StringMessageConverter());
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.afterPropertiesSet();
		stompClient.setReceiptTimeLimit(5000);
		stompClient.setTaskScheduler(taskScheduler);
try{
		stompClient.connect("ws://echo.websocket.org", new StompSessionHandlerAdapter() {
          @Override 
          public void afterConnected(StompSession session, StompHeaders connectedHeaders) { 
              String destination = "/queue/showFlights"; 
            //  LOGGER.debug("Connected to stomp session {} requesting topic {}", session, destination); 
              session.subscribe(destination, new StompFrameHandler(){ 
                  @Override 
                  public Type getPayloadType(StompHeaders headers) { 
                      return String.class; 
                  } 

                  @Override 
                  public void handleFrame(StompHeaders headers, Object payload) { 
                     try { 
                    	 response.addAll(objectMapper.readValue(payload.toString(), SearchResponse.class)); 
                      } catch (IOException e) { 
                         // LOGGER.warn("Unable to parse message as BidVo {}", payload); 
                      } 
                  } 
              }); 

              try { 
                  session.send("iLearn/rxSearch",""); 
              } catch (Exception e) { 
                 e.printStackTrace(); 
             } 
          } 
      });}catch(Exception ex){
    	  ex.printStackTrace();
      }

	return null;
	}
	
	
	public Observable<SearchResponse> searchRxFlightsServer() {
		return Observable.create(new Observable.OnSubscribe<SearchResponse>() {
			@Override
			public void call(Subscriber<? super SearchResponse> t) {
				List<AirlineCommand> alCommands = getAirLineCommands();
				int i=1;
				for(AirlineCommand alCommand : alCommands){
					SearchResponse response = new SearchResponse();
					insertRandomDelay();
					t.onNext(alCommand.execute());
				}
			}

			private void insertRandomDelay() {
				Random rand = new Random();
				try {
					Thread.currentThread().sleep(rand.nextInt(5)*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
    }
	
	

	private void insertDelay(SearchRequest req) {
		if (req.isAsync()) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private List<AirlineCommand> getAirLineCommands() {
		List<AirlineCommand> alCommands = new ArrayList<AirlineCommand>();
		alCommands.add(new GoAir(true,"G8",1000));
		alCommands.add(new SpiceCommand(true,"SG",1000));
		alCommands.add(new IndigoCommand(false,"6E",1000));
		alCommands.add(new MPTBCommand(false,"AI",1000));
		return alCommands;
	}

	

}
