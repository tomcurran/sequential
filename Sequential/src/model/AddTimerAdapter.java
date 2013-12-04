package model;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class AddTimerAdapter extends ClassVisitor {

	private String owner;

	private boolean isInterface;

	public AddTimerAdapter(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & ACC_INTERFACE) != 0;
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (!isInterface && mv != null && !name.equals("<init>")) {
			mv = new AddTimerMethodAdapter(access, name, desc, mv);
		}
		return mv;
	}

	public void visitEnd() {
		if (!isInterface) {
			FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "timer", "J", null, null);
			if (fv != null) {
				fv.visitEnd();
			}
			cv.visitEnd();
		}
	}

	class AddTimerMethodAdapter extends AdviceAdapter {

		private static final String METHOD_START = "%s,%s,>,%d\n";
		private static final String METHOD_END = "%s,%s,<,%d\n";

		private String methodName;

		public AddTimerMethodAdapter(int access, String name, String desc, MethodVisitor mv) {
			super(ASM4, mv, access, name, desc);
			this.methodName = name;
		}

		protected void onMethodEnter() {
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(AddTimerMethodAdapter.METHOD_START);
            mv.visitInsn(ICONST_3);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn(owner);
            mv.visitInsn(AASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitLdcInsn(this.methodName);
            mv.visitInsn(AASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_2);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            mv.visitInsn(AASTORE);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "printf", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;");
            mv.visitInsn(POP);
		}

		protected void onMethodExit(int opcode) {
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(AddTimerMethodAdapter.METHOD_END);
            mv.visitInsn(ICONST_3);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn(owner);
            mv.visitInsn(AASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitLdcInsn(this.methodName);
            mv.visitInsn(AASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_2);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            mv.visitInsn(AASTORE);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "printf", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;");
            mv.visitInsn(POP);
		}

		public void visitMaxs(int maxStack, int maxLocals) {
			super.visitMaxs(maxStack + 5, maxLocals);
		}
	}

}
