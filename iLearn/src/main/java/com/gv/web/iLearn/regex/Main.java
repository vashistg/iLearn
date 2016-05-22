package com.gv.web.iLearn.regex;

public class Main {
	public static void main(String[] args) {
		Employee e = new Employee();
		e.setName("gaurav");
		e.setId("123");
		
		
		Employee e1 = new Employee();
		e1.setName("gaurav");
		e1.setId("123");
		e1.setBoss(e1);
		
		
		e.setBoss(e1);
		
		
		
	}
}
