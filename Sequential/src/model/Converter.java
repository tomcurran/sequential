package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class Converter {
	
	public static String toPlantUML(List<MethodCall> methodsCalled) {
		
		SortedMap<Integer, String> timeline = new TreeMap<Integer,String>();
		
		for(MethodCall m: methodsCalled) {
			String name = m.ExecutingObject.ObjectName +"."+ m.ExecutingObject.ClassName;
			String colour = "#FFBBBB";
			String name2 = "User";
			//String length = String.valueOf((m.CompletedAt-m.CalledAt)/100000);
			if (m.CalledFrom != null) {		
				name2 =  m.CalledFrom.ObjectName +"."+ m.CalledFrom.ClassName;
			}
			
			timeline.put(m.StartId, name2 +" -> "+ name +": "+ m.MethodName +"\nactivate "+ name + colour +"\n");
			timeline.put(m.StopId,  name +" --> "+ name2+": \ndeactivate "+ name +"\n");
			//timeline.put(m.StopId(), "||"+ length +"||\n"+ name +" --> "+ name2+": \ndeactivate "+ name +"\n");
		}
		
		String retString = "@startuml\nactor User\n";

		for(String val: timeline.values()) {
			retString += val;
		}
		
		retString += "@enduml\n";
		
		return retString;
	}
	

}
