package model;

public class  MethodCall {
	
	private static int counter = 0;

	private synchronized static int getId()
    {
        return counter++;
    }
    
	private int StartId;
	
	private int StopId;

	private long CalledAt;
	
	private long CompletedAt;
	
	private String MethodName;
	
	private Object CalledFrom;

	private Object ExecutingObject;
	
	
	public MethodCall(String methodName, Object executingObject) {
		Constructor(methodName, (Object)null, executingObject);
	}

	public MethodCall(String methodName, Object calledFrom, Object executingObject) {
		Constructor(methodName, calledFrom, executingObject);
	}
	
	private void Constructor(String methodName, Object calledFrom, Object executingObject) {		
		MethodName = methodName;
		CalledFrom = calledFrom;
		ExecutingObject = executingObject;
	}
	
	public void Started() {
		StartId = MethodCall.getId();
		CalledAt = System.nanoTime();
	}
	
	public void Completed() {
		StopId = MethodCall.getId();
		CompletedAt = System.nanoTime();
	}
	
	public int StartId(){
		return StartId;
	}
	
	public int StopId(){
		return StopId;
	}
	
	public long CalledAt(){
		return CalledAt;
	}
	
	public long CompletedAt(){
		return CompletedAt;
	}
	
	public String MethodName(){
		return MethodName;
	}
	
	public Object CalledFrom(){
		return CalledFrom;
	}
	
	public Object ExecutingObject(){
		return ExecutingObject;
	}
	


}
