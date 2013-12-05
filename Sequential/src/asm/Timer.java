package asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Timer {

	private static final String METHOD_START = "%s,%s,>,%d\n";
	private static final String METHOD_END = "%s,%s,<,%d\n";

	private static final List<String> calls = new ArrayList<String>();

	public static void start(String className, String methodName) {
		calls.add(String.format(Timer.METHOD_START, className, methodName, System.nanoTime()));
	}

	public static void end(String className, String methodName) {
		calls.add(String.format(Timer.METHOD_END, className, methodName, System.nanoTime()));
	}

	public static void saveToFile(String filename) throws FileNotFoundException {
		String output = "";
		for (String c : Timer.calls) {
			output += c;
		}
		PrintStream out = null;
		    out = new PrintStream(new FileOutputStream(filename));
		    out.print(output);	
		    if (out != null) out.close();
	}

}
