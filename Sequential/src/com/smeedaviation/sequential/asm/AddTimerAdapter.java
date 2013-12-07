package com.smeedaviation.sequential.asm;

import org.objectweb.asm.ClassVisitor;
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
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (!isInterface && mv != null && !name.equals("<init>")) {
			mv = new AddTimerMethodAdapter(access, name, desc, mv);
		}
		return mv;
	}

	class AddTimerMethodAdapter extends AdviceAdapter {

		private String methodName;

		public AddTimerMethodAdapter(int access, String name, String desc, MethodVisitor mv) {
			super(Opcodes.ASM5, mv, access, name, desc);
			this.methodName = name;
		}

		protected void onMethodEnter() {
			mv.visitLdcInsn(owner);
            mv.visitLdcInsn(this.methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/smeedaviation/sequential/asm/Timer", "start", "(Ljava/lang/String;Ljava/lang/String;)V");
		}

		protected void onMethodExit(int opcode) {
			mv.visitLdcInsn(owner);
            mv.visitLdcInsn(this.methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/smeedaviation/sequential/asm/Timer", "end", "(Ljava/lang/String;Ljava/lang/String;)V");
		}

		public void visitMaxs(int maxStack, int maxLocals) {
			super.visitMaxs(maxStack + 2, maxLocals);
		}

	}

}
