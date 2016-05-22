package com.gv.web.iLearn.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement

public class Flight implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1435374575385794202L;
	@JsonProperty("acd")
	protected String airlineCode;
	
	@JsonProperty("fltnum")
	protected String fltNumber;
	
	@JsonProperty("eType")
	protected String engineType;
	
	@JsonProperty("seats")
	protected int seats;
	
	

	public Flight() {
		super();
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	@XmlElement
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public Flight(String airlineCode, String fltNumber, String engineType, int seats) {
		super();
		this.airlineCode = airlineCode;
		this.fltNumber = fltNumber;
		this.engineType = engineType;
		this.seats = seats;
	}
	@JsonProperty
	public String getFltNumber() {
		return fltNumber;
	}
	
	@XmlElement
	public void setFltNumber(String fltNumber) {
		this.fltNumber = fltNumber;
	}

	@JsonProperty
	public String getEngineType() {
		return engineType;
	}
	
	@XmlElement
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	@JsonProperty
	public int getSeats() {
		return seats;
	}

	@XmlElement
	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airlineCode == null) ? 0 : airlineCode.hashCode());
		result = prime * result + ((engineType == null) ? 0 : engineType.hashCode());
		result = prime * result + ((fltNumber == null) ? 0 : fltNumber.hashCode());
		result = prime * result + seats;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (airlineCode == null) {
			if (other.airlineCode != null)
				return false;
		} else if (!airlineCode.equals(other.airlineCode))
			return false;
		if (engineType == null) {
			if (other.engineType != null)
				return false;
		} else if (!engineType.equals(other.engineType))
			return false;
		if (fltNumber == null) {
			if (other.fltNumber != null)
				return false;
		} else if (!fltNumber.equals(other.fltNumber))
			return false;
		if (seats != other.seats)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Flight [airlineCode=" + airlineCode + ", fltNumber=" + fltNumber + ", engineType=" + engineType
				+ ", seats=" + seats + "]";
	}

}
