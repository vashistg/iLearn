package com.gv.owner;

import org.aeonbits.owner.ConfigFactory;

public class PropertyConfigurerTester {
	public static void main(String[] args) {
		PropertyConfigurer cfg = ConfigFactory.create(PropertyConfigurer.class);
		System.out.println("Server " + cfg.hostname() + ":" + cfg.port() +
		                   " will run " + cfg.maxThreads());
	}
}
