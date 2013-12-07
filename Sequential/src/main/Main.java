package main;

import io.ClassReader;
import model.Settings;
import asm.Loader;

public class Main {

	public static void main(String[] args) {
		String pathToJar = args[0];
		String[] applicationArgs = new String[args.length - 1];
		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);

		try {
			ClassReader.UnpackJar(pathToJar, Settings.WORKING_DIRECTORY);
			String mainClassName = ClassReader.GetMainClassName(Settings.WORKING_DIRECTORY);
			Loader.runAnalysis(mainClassName, Settings.METHOD_CALLS, applicationArgs);
			io.Output.writeSVG(Settings.METHOD_CALLS, pathToJar + ".svg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
