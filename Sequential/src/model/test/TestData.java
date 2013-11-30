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
		
		MethodCall mc1 = new MethodCall("testMethod()", test1);		
		MethodCall mc2 = new MethodCall("testMethod2()", test1, test2);		
		MethodCall mc3 = new MethodCall("testMethod3()", test2, test1);
		
		mc1.Started();
		mc2.Started();				
		mc3.Started();
		
		mc3.Completed();	
		mc2.Completed();				
		mc1.Completed();

		testData.add(mc1);
		testData.add(mc2);
		testData.add(mc3);
		
		return testData;
	}
}
