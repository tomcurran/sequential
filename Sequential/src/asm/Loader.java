package asm;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class Loader extends ClassLoader {

	@Override
	protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("java.") || name.startsWith("javax.")
				|| name.startsWith("asm.") || name.startsWith("io.") || name.startsWith("main.") || name.startsWith("model.")) {
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
			cr.accept(cv, 0);
			b = cw.toByteArray();
		} catch (Exception e) {
			throw new ClassNotFoundException(name, e);
		}

		// returns the adapted class
		return defineClass(name, b, 0, b.length);
	}

	public static void runAnalysis(final String className, final String outputFile, final String applicationArgs[]) throws Exception {
		ClassLoader loader = new Loader();
		Class<?> c = loader.loadClass(className);
		Method m = c.getMethod("main", new Class<?>[] { String[].class });
		m.invoke(null, new Object[] { applicationArgs });
		Timer.saveToFile(outputFile);
	}
}
