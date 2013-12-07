package main;

import io.ClassReader;
import io.Output;

import java.io.File;
import java.lang.reflect.Method;

import asm.Loader;
import asm.Timer;

public class Main {

	private static final String WORKING_DIRECTORY = "temp";
	private static final String METHOD_CALLS = WORKING_DIRECTORY + File.separator + "methodcalls.cvs";

	public static void main(String[] args) {
		final String PATH_TO_JAR = args[0];

		String[] applicationArgs = new String[args.length - 1];
		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);

		try {
			ClassReader.UnpackJar(PATH_TO_JAR, WORKING_DIRECTORY);
			ClassLoader loader = new Loader();
			Class<?> c = loader.loadClass(ClassReader.GetMainClassName(WORKING_DIRECTORY));
			Method m = c.getMethod("main", new Class<?>[] { String[].class });
			m.invoke(null, new Object[] { applicationArgs });
			Timer.saveToFile(METHOD_CALLS);
			Output.writeSVG(METHOD_CALLS, PATH_TO_JAR + ".svg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
