package com.smeedaviation.sequential.asm;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class Loader extends ClassLoader {

	@Override
	protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("com.smeedaviation.sequential.")) {
			return super.loadClass(name, resolve);
		}

		// gets an input stream to read the bytecode of the class
		String resource = name.replace('.', '/') + ".class";
		InputStream is = getResourceAsStream(resource);
		byte[] b;

		// adapts the class on the fly
		try {
			ClassReader cr = new ClassReader(is);
			ClassWriter cw = new ClassWriter(0);
			ClassVisitor cv = new AddTimerAdapter(cw);
			cr.accept(cv, ClassReader.EXPAND_FRAMES);
			b = cw.toByteArray();
		} catch (Exception e) {
			throw new ClassNotFoundException(name, e);
		}

		// returns the adapted class
		return defineClass(name, b, 0, b.length);
	}

	@Override
	protected URL findResource(String name) {
		URL url = null;
		try {
			url = new URL("file:temp" + File.separator + name);
		} catch (MalformedURLException e) {
		}
		return url;
	}

}
