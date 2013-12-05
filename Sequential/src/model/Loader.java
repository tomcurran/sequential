package model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class Loader extends ClassLoader {

	private static final String BIN_ADAPT = "bin-adapt";

	@Override
	protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("java.") || name.equals("model.Timer")) {
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

		// optional: stores the adapted class on disk
		try {
			File f = new File(Loader.BIN_ADAPT + File.separator + resource);
			f.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(b);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// returns the adapted class
		return defineClass(name, b, 0, b.length);
	}

	public static void RunAnalysis(final String className, final String outputFile, final String applicationArgs[]) throws Exception {
		// loads the application class (in args[0]) with an Adapt class loader
		ClassLoader loader = new Loader();
		Class<?> c = loader.loadClass(className);
		// calls the 'main' static method of this class with the
		// application arguments (in args[1] ... args[n]) as parameter
		Method m = c.getMethod("main", new Class<?>[] { String[].class });
		m.invoke(null, new Object[] { applicationArgs });
		Timer.saveToFile(outputFile);
	}
}
