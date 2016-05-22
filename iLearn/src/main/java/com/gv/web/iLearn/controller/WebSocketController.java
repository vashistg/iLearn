package com.gv.web.iLearn.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gv.async.websocket.CalcInput;
import com.gv.async.websocket.Result;
import com.gv.web.iLearn.pojo.SearchResponse;
import com.gv.web.iLearn.service.FlightService;
import com.gv.web.iLearn.service.impl.FlightServiceAPI;

import rx.Subscriber;

@Controller
public class WebSocketController {
	
	@Autowired
    public SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/add" )
    @SendTo("/topic/showResult")
    public Result addNum(CalcInput input) throws Exception {
        //Thread.sleep(2000);
        Result result = new Result(input.getNum1()+"+"+input.getNum2()+"="+(input.getNum1()+input.getNum2())); 
        return result;
    }
	
	@MessageMapping("/rxSearch" )
    @SendTo("/queue/showFlights")
	public SearchResponse rxSearchFlights() throws IOException {
		final FlightService service = new FlightServiceAPI();
		final SearchResponse finalResponse= new SearchResponse();
		service.searchRxFlights().subscribe(new Subscriber<SearchResponse>() {
			@Override
			public void onCompleted() {
			}
			@Override
			public void onError(Throwable e) {
			}
			@Override
			public void onNext(SearchResponse reponse) {
				messagingTemplate.convertAndSend("/queue/showFlights",reponse);
			}
		});
		
		return finalResponse;
	}
	
	
	
    @RequestMapping("/start")
    public String start() {
        return "start";
    }
}
