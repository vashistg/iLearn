package com.gv.web.iLearn.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import com.gv.web.iLearn.annotation.Author;
import com.gv.web.iLearn.annotation.Author.Priority;
import com.gv.web.iLearn.annotation.Author.Status;

@JsonAutoDetect
@XmlRootElement
@Author(author="gv",priority=Priority.HIGH,status=Status.NOT_STARTED)
public class SearchResponse {

	@JsonProperty("flight")
	protected List<Flight> flights = new ArrayList<Flight>();
	
	@JsonProperty("error")
	protected Error error ;
	
	
	public void addAll(SearchResponse newResponse){
		this.flights.addAll(newResponse.getFlights());
	}
	
	
	public List<Flight> getFlights() {
		return flights;
	}
	
	@XmlElement
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	public Error getError() {
		return error;
	}
	
	@XmlElement
	public void setError(Error error) {
		this.error = error;
	}
	
	
}
