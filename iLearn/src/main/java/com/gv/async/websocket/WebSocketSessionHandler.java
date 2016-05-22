package com.gv.async.websocket;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.gv.web.iLearn.pojo.SearchResponse;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter {

	public SearchResponse response = new SearchResponse();
	@Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/queue/showFlights", new StompFrameHandler() {
			
			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				response.addAll((SearchResponse)payload);
			}
			
			@Override
			public Type getPayloadType(StompHeaders headers) {
				// TODO Auto-generated method stub
				return null;
			}
		});
    }
	
	
	
}
