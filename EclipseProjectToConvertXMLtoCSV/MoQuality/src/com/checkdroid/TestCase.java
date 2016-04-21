package com.checkdroid;

public class TestCase {
	String name;
	String classname;
	float time;
	boolean passed;
	String failureMessage = "";
	String failureType = "";
	
	public TestCase(String name, String classname, float time, boolean passed){
		this.name = name;
		this.classname = classname;
		this.time = time;
		this.passed = passed;
	}
}
