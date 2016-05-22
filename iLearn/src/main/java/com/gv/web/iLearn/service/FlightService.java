package com.gv.web.iLearn.service;

import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.pojo.SearchResponse;

import rx.Observable;

public interface FlightService {

	public SearchResponse searchFlights(SearchRequest request);
	public Observable<SearchResponse> searchRxFlights();
	
}
