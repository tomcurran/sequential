package com.smeedaviation.sequential.main;

import java.io.File;
import java.lang.reflect.Method;

import com.smeedaviation.sequential.asm.Loader;
import com.smeedaviation.sequential.asm.Timer;
import com.smeedaviation.sequential.io.ClassReader;
import com.smeedaviation.sequential.io.Output;

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
