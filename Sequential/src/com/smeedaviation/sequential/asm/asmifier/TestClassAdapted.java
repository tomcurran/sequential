package com.smeedaviation.sequential.asm.asmifier;

import com.smeedaviation.sequential.asm.Timer;

public class TestClassAdapted {

	private String testField;

	public TestClassAdapted() {
		this.testField = "testFieldString";
	}

	public void testMethod1() {
		Timer.start("TestClass", "testMethod1");

		// actual method start
		System.out.println("testMethod1 excuting...");
		// actual method end

		Timer.end("TestClass", "testMethod1");
	}

	public void testMethod2() {
		Timer.start("TestClass", "testMethod2");

		// actual method start
		System.out.println("testMethod2 excuting..." + this.testField);
		// actual method end

		Timer.end("TestClass", "testMethod2");
	}

	public static void main(String[] args) {
		TestClassAdapted tc = new TestClassAdapted();
		tc.testMethod1();
		tc.testMethod2();
	}

}