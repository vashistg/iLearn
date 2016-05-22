package com.gv.web.iLearn.pojo;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Employee {
	
	private String name;
	private String id;
	private double salary;
	
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	@XmlElement
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	

}
