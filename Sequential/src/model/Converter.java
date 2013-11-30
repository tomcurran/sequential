package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class Converter {
	
	public static String toPlantUML(List<MethodCall> methodsCalled) throws Exception {
		
		SortedMap<Integer, String> timeline = new TreeMap<Integer,String>();
		
		double totalDuration = 0;
		for(MethodCall m: methodsCalled) {
			totalDuration += (double)m.Duration();
		}

		
		for(MethodCall m: methodsCalled) {
			String name = m.ExecutingObject().ObjectName +"."+ m.ExecutingObject().ClassName;
			String name2 = "User";
			//String length = String.valueOf((m.CompletedAt-m.CalledAt)/100000);
			String colour;
			double  timePercentage = (double)m.Duration()/totalDuration;
						
			if (timePercentage < 0.1) {
				colour = " #LIGHTBLUE";
			} else if (timePercentage < 0.2) {
				colour = " #LIGHTYELLOW";
			} else if (timePercentage < 0.3) {
				colour = " #YELLOW";
			} else if (timePercentage < 0.4) {
				colour = " #LIGHTGREEN";
			} else if (timePercentage < 0.5) {
				colour = " #GREEN";
			} else if (timePercentage < 0.6) {
				colour = " #DARKGREEN";
			} else if (timePercentage < 0.7) {
				colour = " #ORANGE";
			} else if (timePercentage < 0.8) {
				colour = " #RED";
			} else if (timePercentage < 0.9) {
				colour = " #8F00FF";
			} else if (timePercentage <= 1) {
				colour = " #BLUE";
			} else {
				throw new Exception("Invalid percentage"); 
			}
			
			if (m.CalledFrom() != null) {		
				name2 =  m.CalledFrom().ObjectName +"."+ m.CalledFrom().ClassName;
			}
			
			timeline.put(m.StartId(), name2 +" -> "+ name +": "+ m.MethodName() +"\nactivate "+ name + colour +"\n");
			timeline.put(m.StopId(),  name +" --> "+ name2+": \ndeactivate "+ name +"\n");
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
