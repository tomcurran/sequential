package classReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.PatternSyntaxException;

public class ClassReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	if (args.length == 0) {
		System.out.println("Missing directory or jar");
	}
	
	String path = args[0];
	
	if (path.endsWith(".jar")) {
		File outputfolder = new File("temp");
		outputfolder.mkdirs();
		
		try {
			UnpackJar(path, outputfolder.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		path = outputfolder.getPath();
	}
	
	try {
		File manifest = ScanDir(path);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	}
	
	private static void UnpackJar(String jarFile, String destDir) throws IOException {
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> es = jar.entries();
		while (es.hasMoreElements()) {
		    java.util.jar.JarEntry file = (java.util.jar.JarEntry) es.nextElement();
		    java.io.File outFile = new java.io.File(destDir + java.io.File.separator + file.getName());
		    
		    if(!outFile.exists())
	        {
		    	outFile.getParentFile().mkdirs();
		    	outFile = new java.io.File(destDir, file.getName());
	        }
	        if(file.isDirectory())
	        {
	            continue;
	        }
	    	
		    java.io.InputStream is = jar.getInputStream(file); // get the input stream
		    java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile);
		    while (is.available() > 0) {  // write contents of 'is' to 'fos'
		    	fos.write(is.read());
		    }
		    fos.close();
		    is.close();
		}
		
		jar.close();
	}
	
//	private static List<File> ScanDir(String dirPath, List<File> files) {
//		File directory = new File(dirPath);
//
//	    // get all the files from a directory
//	    File[] fList = directory.listFiles();
//	    for (File file : fList) {
//	        if (file.isFile() && file.getName().endsWith(".class")) {
//	            files.add(file);
//	        } else if (file.isDirectory()) {
//	            ScanDir(file.getAbsolutePath(), files);
//	        }
//	    }
//	    
//	    return files;
//	}
//	
	private static File ScanDir(String dirPath) throws Exception {
		File directory = new File(dirPath);
		File[] manifest = directory.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.equals("META-INA") || name.equals("MANIFEST.MF");
			}
		});
		
		if (manifest.length == 0) {
			throw new Exception("No manifest.mf found in jar");
		}
		
		if (manifest[0].isDirectory()) {
			return ScanDir(manifest[0].getPath());
		}
		
		return manifest[0];
		}

	
	private static File LocateMainClass(File manifest) {
		Scanner scanner =  new Scanner(manifest.getPath());
		while (scanner.hasNextLine()) {
			
			Scanner line = new Scanner(scanner.nextLine());
			line.useDelimiter(":");
		    if (line.hasNext()) {
		    	if (!line.next().equals("Main-Class")) {
		    		continue;
		    	}
		    }
		      String name = ;
		      String value = line.next();
		}
		
		return null;
		
	}
}
