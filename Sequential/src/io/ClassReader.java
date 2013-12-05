package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassReader {

	public static String GetMainClassName(String dir) throws Exception {
		File manifest = ScanDir(dir);
		return MainClassPath(manifest);
	}

	public static void UnpackJar(String jarFile, String destDir) throws IOException {
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> es = jar.entries();
		while (es.hasMoreElements()) {
			JarEntry file = (JarEntry) es.nextElement();
			File outFile = new File(destDir + File.separator + file.getName());

			if (!outFile.exists()) {
				outFile.getParentFile().mkdirs();
				outFile = new File(destDir, file.getName());
			}

			if (file.isDirectory()) {
				continue;
			}

			InputStream is = jar.getInputStream(file); // get the input stream
			FileOutputStream fos = new FileOutputStream(outFile);

			while (is.available() > 0) { // write contents of 'is' to 'fos'
				fos.write(is.read());
			}

			fos.close();
			is.close();
		}

		jar.close();
	}

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
		Scanner scanner = new Scanner(manifest);
		try {
			while (scanner.hasNextLine()) {
				Scanner line = new Scanner(scanner.nextLine());
				line = line.useDelimiter(":");
				try {
					if (line.hasNext()) {
						if (line.next().equals("Main-Class")) {
							String value = line.next().trim();
							line.close();
							return value;
						}
					}
				} finally {
					line.close();
				}
			}
			throw new Exception("No Main-Class defined in manifest.mf");
		} finally {
			scanner.close();
		}
	}

}
