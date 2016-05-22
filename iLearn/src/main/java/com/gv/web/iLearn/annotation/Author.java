package com.gv.web.iLearn.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Author {
	public enum Priority {LOW, MEDIUM, HIGH}
	public enum Status {STARTED, NOT_STARTED}
	String author() default "Yash";
	Priority priority() default Priority.LOW;
	Status status() default Status.NOT_STARTED;
	
}
