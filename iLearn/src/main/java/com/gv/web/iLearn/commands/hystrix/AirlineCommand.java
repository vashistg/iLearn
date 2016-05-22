package com.gv.web.iLearn.commands.hystrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gv.web.iLearn.pojo.Flight;
import com.gv.web.iLearn.pojo.SearchResponse;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class AirlineCommand extends HystrixCommand<SearchResponse>{

	DynamicStringProperty failDynamic = DynamicPropertyFactory.getInstance().getStringProperty("stringprop", "gv");
	
	boolean failProp = false; 
	String ailineSpecificCommand ="";
	Logger logger = LoggerFactory.getLogger(AirlineCommand.class);
	
	
	protected AirlineCommand(boolean failCheck,String airlinespecificCommand,int timeout) {
		
		super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Search"))
				.andCommandKey(HystrixCommandKey.Factory.asKey(airlinespecificCommand))
        		.andCommandPropertiesDefaults(
        		      HystrixCommandProperties.Setter()
                .withCircuitBreakerEnabled(true)
                .withCircuitBreakerSleepWindowInMilliseconds(60*60*1000)
                .withCircuitBreakerRequestVolumeThreshold(5)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withExecutionTimeoutInMilliseconds(timeout)
                )
		        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10).withQueueSizeRejectionThreshold(10)));
		this.failProp = failCheck;
		//System.out.println(failDynamic.get());
		this.ailineSpecificCommand =airlinespecificCommand;
	}
	
	@Override
	protected SearchResponse run() throws Exception {
		System.out.println("Run::CircuitBreaker::"+this.isCircuitBreakerOpen());
		/**
		 * Airline specific code will go here.
		 */
		if(failProp){
			throw new RuntimeException("Got some Exception" );
		}else{
			SearchResponse response = new SearchResponse();
			List<Flight> flights = new ArrayList<Flight>();
		    for(int i=0;i<5;i++){
		    	Flight flt =  new Flight(this.ailineSpecificCommand,"123"+i,"84"+i%7,360);	
		    	flights.add(flt);
		    }
		    response.setFlights(flights);
			return response;
		}
	}
	
	@Override
	protected SearchResponse getFallback() {
		// TODO Auto-generated method stub
		System.out.println("Fallback::CircuitBreaker::"+this.isCircuitBreakerOpen());
		if(this.isCircuitBreakerOpen()){
			logger.error(getErrorMessage());
		}
		SearchResponse response = new SearchResponse();
		response.setError(new Error("Exception While Searching"));
		return response;
	}

	private String getErrorMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(" Circuit Breaker enabled  for ").
        append( this.getCommandGroup().name() +" " +this.getCommandKey().name()).
        append(" @ ").append(new Date()).
        append(" for ").append(this.getProperties().circuitBreakerSleepWindowInMilliseconds().get()/1000).
        append(" seconds ");
		return builder.toString();
	}

}
