package model;

public class MethodCall {

	private static int counter = 0;

	private synchronized static int getId() {
		return counter++;
	}

	private int startId;
	private int endId;
	private long startTime;
	private long endTime;
	private long duration;
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
		this.duration = -1;
	}

	public void start() {
		this.startId = MethodCall.getId();
		this.startTime = System.nanoTime();
	}

	public void end() {
		this.endId = MethodCall.getId();
		this.endTime = System.nanoTime();
		this.duration = this.endTime - this.startTime;
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