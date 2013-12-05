package main;

import io.ClassReader;

import java.io.File;
import asm.Loader;

public class Main {

	public static void main(String[] args) {
		String pathToJar = args[0];
		String outputPath = args[1];
		String workingDir = "temp";
		String interFile =  workingDir + File.separator + "inter.txt";
 		String[] applicationArgs = new String[args.length - 2];
 		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);	
 		
		try {
			ClassReader.UnpackJar(pathToJar, workingDir);
			String mainClassName = ClassReader.GetMainClassName(workingDir);
			Loader.RunAnalysis(mainClassName, interFile, applicationArgs);
			io.Output.WriteSVG(interFile, outputPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

}
