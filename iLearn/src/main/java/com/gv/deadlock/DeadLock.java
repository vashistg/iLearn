package com.gv.deadlock;

public class DeadLock {
	
	static String name = "gv";
	static String id = "123";
	
	
	public void run(){
		synchronized (name) {
			System.out.println("run man run");
			synchronized (id) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("running man running");
			}
		}
	}
	
	public void dontRun(){
		synchronized (id) {
			System.out.println("don't run man");
			synchronized (name) {
				System.out.println("mar gaya");
			}
		}
	}
	
}
