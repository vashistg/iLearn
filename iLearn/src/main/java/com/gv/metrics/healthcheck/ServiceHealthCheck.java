package com.gv.metrics.healthcheck;

import java.util.List;

import com.codahale.metrics.health.HealthCheck;
import com.gv.web.iLearn.pojo.Flight;
import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.service.impl.FlightServiceAPI;

public class ServiceHealthCheck extends HealthCheck{


	FlightServiceAPI api =  new FlightServiceAPI();
	@Override
	protected Result check() throws Exception {
		SearchRequest req=  new SearchRequest();
		req.setAsync(false);
		List<Flight> flights =api.searchFlights(req).getFlights();
		if(flights != null && flights.size() > 0 ){
			return Result.healthy("API is responding fit and fine.");
		}
		return Result.unhealthy("API is not responding. Please check.");
	}
}
