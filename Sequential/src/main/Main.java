package main;

import java.io.File;
import java.io.IOException;

import model.Loader;
import view.PlantUMLTest;
import classReader.ClassReader;

public class Main {

	public static void main(String[] args) {
		String pathToJar = args[0];
		String outputPath = args[1];
		String workingDir = "temp";
		String interFile =  workingDir + File.pathSeparator + "inter.txt";
		
		try {
			ClassReader.UnpackJar(pathToJar, workingDir);
			String mainClassName = ClassReader.GetMainClassName(workingDir);
			// call toms asm code with workdir and mainclassname
	 		String[] applicationArgs = new String[args.length - 2];
	 		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);
	 		
			Loader.RunAnalysis(mainClassName, interFile, applicationArgs);
			PlantUMLTest.WriteSVG(interFile, outputPath);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
