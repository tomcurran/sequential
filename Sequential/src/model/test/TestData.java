package model.test;

import java.util.ArrayList;
import java.util.List;

import model.MethodCall;

public class TestData {

	public static List<MethodCall> get() {
		List<MethodCall> testData = new ArrayList<MethodCall>();

		MethodCall mc1 = new MethodCall("testMethod", "Class1");
		MethodCall mc2 = new MethodCall("testMethod2", "Class1", "Class2");
		MethodCall mc3 = new MethodCall("testMethod3", "Class2", "Class1");

		mc1.start();
		mc2.start();
		mc3.start();

		mc3.end();
		mc2.end();
		mc1.end();

		testData.add(mc1);
		testData.add(mc2);
		testData.add(mc3);

		return testData;
	}
}