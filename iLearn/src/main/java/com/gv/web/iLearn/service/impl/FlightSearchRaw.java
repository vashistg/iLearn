package com.gv.web.iLearn.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gv.web.iLearn.commands.hystrix.AirlineCommand;
import com.gv.web.iLearn.commands.hystrix.GoAir;
import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.pojo.SearchResponse;

public class FlightSearchRaw {
	public SearchResponse searchFlights(SearchRequest request) {
		SearchResponse response = new SearchResponse();
		insertDelay(request);
		List<AirlineCommand> alCommands = getAirLineCommands(request);
		for(AirlineCommand alCommand : alCommands){
			response.addAll(alCommand.execute());
		}
		return response;
	}

	private void insertDelay(SearchRequest req) {
		if (req.isAsync()) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private List<AirlineCommand> getAirLineCommands(SearchRequest request) {
		List<AirlineCommand> alCommands = new ArrayList<AirlineCommand>();
		alCommands.add(new GoAir(false,"",1000));
		return alCommands;
	}
}
