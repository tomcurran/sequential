package model.test;

public class TestClass {

	private String testField;

	public TestClass() {
		this.testField = "testFieldString";
	}

	public void testMethod1() {

		// actual method start
		System.out.println("testMethod1 excuting...");
		// actual method end

	}

	public void testMethod2() {

		// actual method start
		System.out.println("testMethod2 excuting..." + this.testField);
		// actual method end

	}

	public static void main(String[] args) {
		TestClass tc = new TestClass();
		tc.testMethod1();
		tc.testMethod2();
	}

}