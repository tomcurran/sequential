package main;

import io.ClassReader;

import java.io.File;
import asm.Loader;
import model.Settings;

public class Main {

	public static void main(String[] args) {
		String pathToJar = args[0];
		String outputPath = args[1];
		String interFile = Settings.WORKING_DIRECTORY + File.separator + "inter.txt";
		String[] applicationArgs = new String[args.length - 2];
		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);

		try {
			ClassReader.UnpackJar(pathToJar, Settings.WORKING_DIRECTORY);
			String mainClassName = ClassReader.GetMainClassName(Settings.WORKING_DIRECTORY);
			Loader.RunAnalysis(mainClassName, interFile, applicationArgs);
			io.Output.WriteSVG(interFile, outputPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
