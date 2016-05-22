package com.gv.web.iLearn.controller;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.pojo.SearchResponse;
import com.gv.web.iLearn.service.FlightService;
import com.gv.web.iLearn.service.impl.FlightServiceAPI;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Api(value = "Swag Controller .This annotates all the API description here.", description = "I am a controller with some swag.")
@Controller
@RequestMapping("/api")
public class SwagController {
	
	@Autowired
	FlightService flightServiceProxy;

	
	@RequestMapping(value="/test")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		ModelAndView mv = new ModelAndView("home");
		FlightService service = new FlightServiceAPI();
		SearchResponse searchResponse = service.searchFlights(new SearchRequest());
		mv.addObject("response", searchResponse);
		System.out.println("Sync Method" + Thread.currentThread().getName());
		return mv;
	}
	
	
	@ApiOperation(notes="This is a test Controller", value = "gv is testing it",responseClass="SearchResponse.class")
	@RequestMapping(value="/rest")
	public @ResponseBody SearchResponse restAPI(@RequestParam String name ,HttpServletResponse response) throws IOException{
		FlightService service = new FlightServiceAPI();
		SearchRequest req = new SearchRequest();
		req.setAsync(false);	
		SearchResponse searchResponse = service.searchFlights(req);
		return searchResponse;
	}
	
	@RequestMapping(value="/aop")
	public @ResponseBody SearchResponse aopAPI(HttpServletResponse response) throws IOException{
		/**
		 * Need to understad this concept.
		 * Proxying in java
		 * CGLIB is used if the class does not implement any interface. It will create a sub class
		 * extending the base class. 
		 * JDK Proxy is used if the class implements some interface 
		 * 
		**/
		/*ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "mvc-dispatcher-servlet.xml" });
		FlightSearchRaw searchAPI = (FlightSearchRaw)appContext.getBean("flightServiceProxy");*/
		SearchRequest req = new SearchRequest();
		req.setAsync(false);
		return flightServiceProxy.searchFlights(req);
	}
	
	@RequestMapping(value="/asyncTask")	
	public @ResponseBody Callable<SearchResponse> callableAPI(HttpServletResponse response) throws IOException{
		final FlightService service = new FlightServiceAPI();
		Callable<SearchResponse> asyncTask = new Callable<SearchResponse>() {
			@Override
			public SearchResponse call() throws Exception {
				System.out.println("Callbale thread" + Thread.currentThread().getName());
				SearchRequest req = new SearchRequest();
				req.setAsync(true);	
				SearchResponse searchResponse = service.searchFlights(req);
				return searchResponse;
			}	
		};
		System.out.println("Async Method" + Thread.currentThread().getName());
		return asyncTask;
	}
	
	@RequestMapping(value="/forceOpen")	
	public @ResponseBody boolean callableAPI(String keyName , HttpServletResponse response) throws IOException{
		
		
		return true;
	}
	
	
}
