package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class Converter {
	
	public static String toPlantUML(List<MethodCall> methodsCalled) {
		//";
//		source += "";
//		source += "User -> A: DoWork\n";
//		source += "activate A #FFBBBB\n";
////		source += "A -> A: Internal call\n";
////		source += "activate A #DarkSalmon\n";
////		source += "A -> B: << createRequest >>\n";
////		source += "activate B\n";
////		source += "B --> A: RequestCreated\n";
////		source += "deactivate B\n";
////		source += "deactivate A\n";
//		source += "A -> User: Done\n";
//		source += "deactivate A\n";
//		source += "";
		
		SortedMap<Long, String> timeline = new TreeMap<Long,String>();
		
		for(MethodCall m: methodsCalled) {
			String name = m.ExecutingObject.ObjectName +"."+ m.ExecutingObject.ClassName;
			String colour = "#FFBBBB";
			String name2 = "User";
			String length = String.valueOf((m.CompletedAt-m.CalledAt)/100000);
			if (m.CalledFrom != null) {		
				name2 =  m.CalledFrom.ObjectName +"."+ m.CalledFrom.ClassName;
			}
			
			timeline.put(m.CalledAt, name2 +" -> "+ name +": "+ m.MethodName +"\nactivate "+ name + colour +"\n");
			timeline.put(m.CompletedAt, "||"+ length +"||\n"+ name +" --> "+ name2+": \ndeactivate "+ name +"\n");
		}
		
		String retString = "@startuml\nactor User\n";

		for(String val: timeline.values()) {
			retString += val;
		}
		
		retString += "@enduml\n";
		
		return retString;
	}
	

}
