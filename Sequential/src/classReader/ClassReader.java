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

	public static String GetMainClassName(String dir) throws Exception {
		File manifest = ScanDir(dir);
		return MainClassPath(manifest);
	}
	
	public static void UnpackJar(String jarFile, String destDir) throws IOException {
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
				return name.equals("META-INF") || name.equals("MANIFEST.MF");
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

	
	private static String MainClassPath(File manifest) throws Exception {
		Scanner scanner =  new Scanner(manifest);
		while (scanner.hasNextLine()) {
			String l = scanner.nextLine();
			Scanner line = new Scanner(l).useDelimiter(":");
		    if (line.hasNext()) {
		    	String name = line.next();
		    	if (name.equals("Main-Class")) {
			    	String value = line.next();
		    		return value;
		    	}
		    }
		}
		
		throw new Exception("No Main-Class defined in manifest.mf");
	}
}
