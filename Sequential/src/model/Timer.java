package model;

public class Timer {

	private static final String METHOD_START = "%s.%s>%d\n";
	private static final String METHOD_END = "%s.%s>%d\n";

	public static void start(String className, String methodName) {
		System.err.printf(Timer.METHOD_START, className, methodName, System.nanoTime());
	}

	public static void end(String className, String methodName) {
		System.err.printf(Timer.METHOD_END, className, methodName, System.nanoTime());
	}

}
