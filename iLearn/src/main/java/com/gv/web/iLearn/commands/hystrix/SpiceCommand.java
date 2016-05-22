package com.gv.web.iLearn.commands.hystrix;

public class SpiceCommand extends AirlineCommand{
	
	public SpiceCommand(boolean failCheck, String airlinespecificCommand,int timeout) {
		super(failCheck, airlinespecificCommand,timeout);
	}
	
}
