package main;

import io.ClassReader;

import java.io.File;
import asm.Loader;
import model.Settings;

public class Main {
	

	public static void main(String[] args) {
		String pathToJar = args[0];
		String outputPath = args[1];
		String interFile = Settings.workingDir + File.separator + "inter.txt";
 		String[] applicationArgs = new String[args.length - 2];
 		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);	
 		
		try {
			ClassReader.UnpackJar(pathToJar, Settings.workingDir);
			String mainClassName = ClassReader.GetMainClassName(Settings.workingDir);
			Loader.RunAnalysis(mainClassName, interFile, applicationArgs);
			io.Output.WriteSVG(interFile, outputPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

}
