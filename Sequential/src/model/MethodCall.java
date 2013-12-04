package model;

public class MethodCall {

	private static int counter = 0;

	protected synchronized static int getId() {
		return ++counter;
	}

	protected int startId;
	protected int endId;
	protected long startTime;
	protected long endTime;
	protected long duration;
	private String methodName;
	private String invokingClass;
	private String exicutingClass;

	public MethodCall(String methodName, String exicutingClass) {
		this(methodName, null, exicutingClass);
	}

	public MethodCall(String methodName, String invokingClass, String exicutingClass) {
		this.methodName = methodName;
		this.invokingClass = invokingClass;
		this.exicutingClass = exicutingClass;
		this.startId = -1;
		this.endId = -1;
		this.startTime = -1;
		this.endTime = -1;
		this.duration = 0;
	}

	public void start() {
		if (this.startId < 0) {
				this.startId = MethodCall.getId();
				this.startTime = System.nanoTime();
		}
	}

	public void end() {
		if (this.startId > 0 && this.endId < 0) {
			this.endId = MethodCall.getId();
			this.endTime = System.nanoTime();
			this.duration = this.endTime - this.startTime;
		}
	}

	public int getStartId() {
		return startId;
	}

	public int getEndId() {
		return this.endId;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public long getEndTime() {
		return this.endTime;
	}

	public long getDuration() {
		return this.duration;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getInvokingClass() {
		return this.invokingClass;
	}

	public String getExicutingClass() {
		return this.exicutingClass;
	}

}