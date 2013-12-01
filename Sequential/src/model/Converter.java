package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class Converter {
	
	private static String LIGHTBLUE = "LIGHTBLUE";
	private static String LIGHTYELLOW = "LIGHTYELLOW";
	private static String YELLOW = "YELLOW";
	private static String LIGHTGREEN = "LIGHTGREEN";
	private static String GREEN = "GREEN";
	private static String DARKGREEN = "DARKGREEN";
	private static String ORANGE = "ORANGE";
	private static String RED = "RED";
	private static String INDIGO = "INDIGO";
	private static String BLUE = "BLUE";
	
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
						
			if (timePercentage <= 0.1) {
				colour = LIGHTBLUE;
			} else if (timePercentage <= 0.2) {
				colour = LIGHTYELLOW;
			} else if (timePercentage <= 0.3) {
				colour = YELLOW;
			} else if (timePercentage <= 0.4) {
				colour = LIGHTGREEN;
			} else if (timePercentage <= 0.5) {
				colour = GREEN;
			} else if (timePercentage <= 0.6) {
				colour = DARKGREEN;
			} else if (timePercentage <= 0.7) {
				colour = ORANGE;
			} else if (timePercentage <= 0.8) {
				colour = RED;
			} else if (timePercentage <= 0.9) {
				colour = INDIGO;
			} else if (timePercentage <= 1) {
				colour = BLUE;
			} else {
				throw new Exception("Invalid percentage"); 
			}
			
			if (m.CalledFrom() != null) {		
				name2 =  m.CalledFrom().ObjectName +"."+ m.CalledFrom().ClassName;
			}
			
			timeline.put(m.StartId(), name2 +" -> "+ name +": "+ m.MethodName() +"\nactivate "+ name + " #"+ colour +"\n");
			timeline.put(m.StopId(),  name +" --> "+ name2+": \ndeactivate "+ name +"\n");
			//timeline.put(m.StopId(), "||"+ length +"||\n"+ name +" --> "+ name2+": \ndeactivate "+ name +"\n");
		}
		
		String colours = "<size:18><back:"+ LIGHTBLUE +"><color:"+ LIGHTBLUE + "><b><u>"+ LIGHTBLUE +"</u></b></color></back> 0-10%</size>\n";
		colours += "<size:18><back:"+ LIGHTYELLOW +"><color:"+ LIGHTYELLOW + "><b><u>"+ LIGHTYELLOW +"</u></b></color></back> 11-20%</size>\n";
		colours += "<size:18><back:"+ YELLOW +"><color:"+ YELLOW + "><b><u>"+ YELLOW +"</u></b></color></back> 21-30%</size>\n";
		colours += "<size:18><back:"+ LIGHTGREEN +"><color:"+ LIGHTGREEN + "><b><u>"+ LIGHTGREEN +"</u></b></color></back> 31-40%</size>\n";
		colours += "<size:18><back:"+ GREEN +"><color:"+ GREEN + "><b><u>"+ GREEN +"</u></b></color></back> 41-50%</size>\n";
		colours += "<size:18><back:"+ DARKGREEN +"><color:"+ DARKGREEN + "><b><u>"+ DARKGREEN +"</u></b></color></back> 51-60%</size>\n";
		colours += "<size:18><back:"+ ORANGE +"><color:"+ ORANGE + "><b><u>"+ ORANGE +"</u></b></color></back> 61-70%</size>\n";
		colours += "<size:18><back:"+ RED +"><color:"+ RED + "><b><u>"+ RED +"</u></b></color></back> 71-80%</size>\n";
		colours += "<size:18><back:"+ INDIGO +"><color:"+ INDIGO + "><b><u>"+ INDIGO +"</u></b></color></back> 81-90%</size>\n";
		colours += "<size:18><back:"+ BLUE +"><color:"+ BLUE + "><b><u>"+ BLUE +"</u></b></color></back> 91-100%</size>\n";
		String retString = "@startuml\nactor User\nlegend\nTime Exicuting\n"+ colours +"\nendlegend\n";

		for(String val: timeline.values()) {
			retString += val;
		}
		
		retString += "@enduml\n";
		
		return retString;
	}
	

}
