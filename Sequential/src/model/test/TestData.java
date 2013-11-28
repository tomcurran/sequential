package model.test;

import java.util.ArrayList;
import java.util.List;

import model.*;
import model.Object;

public class TestData {

	public static List<MethodCall> get() {
		List<MethodCall> testData = new ArrayList<MethodCall>();
		
		Object test1 = new Object();
		test1.ClassName = "Class1";
		test1.ObjectName = "Object1";
		
		Object test2 = new Object();
		test2.ClassName = "Class2";
		test2.ObjectName = "Object2";
		
		MethodCall mc1 = new MethodCall();
		mc1.MethodName = "testMethod()";
		mc1.CalledFrom = null;
		mc1.CalledAt = System.nanoTime();		
		mc1.ExecutingObject = test1;
		
		MethodCall mc2 = new MethodCall();
		mc2.MethodName = "testMethod2()";
		mc2.CalledFrom = test1;
		mc2.CalledAt = System.nanoTime();
		mc2.ExecutingObject = test2;
		
		
		MethodCall mc3 = new MethodCall();
		mc3.MethodName = "testMetho3()";
		mc3.CalledFrom = test2;
		mc3.CalledAt = System.nanoTime();
		mc3.ExecutingObject = test1;
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc3.CompletedAt = System.nanoTime();
		
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc2.CompletedAt = System.nanoTime();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mc1.CompletedAt = System.nanoTime();

		

		

		testData.add(mc1);
		testData.add(mc2);
		testData.add(mc3);
		
		return testData;
	}
}
