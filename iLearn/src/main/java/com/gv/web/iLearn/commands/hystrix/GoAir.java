package com.gv.web.iLearn.commands.hystrix;

public class GoAir extends AirlineCommand{

	public GoAir(boolean failCheck,String someAirlineSpecificCommand,int timeout) {
		super(failCheck,someAirlineSpecificCommand,timeout); 
	}

}
