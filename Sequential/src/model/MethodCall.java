package model;

public class  MethodCall {
	
	static int counter = 0;

    public synchronized static int getId()
    {
        return counter++;
    }
    
	public int StartId;
	
	public int StopId;

    public String MethodName;
	
	public Object CalledFrom;

	public Object ExecutingObject;
	
	public long CalledAt;
	
	public long CompletedAt;

}
