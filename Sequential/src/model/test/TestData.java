package model.test;

import java.util.ArrayList;
import java.util.List;

import model.MethodCall;
import model.SeqObject;

public class TestData {

	public static List<MethodCall> get() {
		List<MethodCall> testData = new ArrayList<MethodCall>();

		SeqObject test1 = new SeqObject("Object1", "Class1");
		SeqObject test2 = new SeqObject("Object2", "Class2");

		MethodCall mc1 = new MethodCall("testMethod", test1);
		MethodCall mc2 = new MethodCall("testMethod2", test1, test2);
		MethodCall mc3 = new MethodCall("testMethod3", test2, test1);

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