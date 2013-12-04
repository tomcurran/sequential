package model.test;

public class TestClassAdapted {

	private String testField;

	public TestClassAdapted() {
		this.testField = "testFieldString";
	}

	public void testMethod1() {
		System.err.printf("%s.%s>%d\n", "TestClass", "testMethod1", System.nanoTime());

		// actual method start
		System.out.println("testMethod1 excuting...");
		// actual method end

		System.err.printf("%s.%s<%d\n", "TestClass", "testMethod1", System.nanoTime());
	}

	public void testMethod2() {
		System.err.printf("%s.%s>%d\n", "TestClass", "testMethod2", System.nanoTime());

		// actual method start
		System.out.println("testMethod2 excuting..." + this.testField);
		// actual method end

		System.err.printf("%s.%s<%d\n", "TestClass", "testMethod2", System.nanoTime());
	}

	public static void main(String[] args) {
		TestClassAdapted tc = new TestClassAdapted();
		tc.testMethod1();
		tc.testMethod2();
	}

}