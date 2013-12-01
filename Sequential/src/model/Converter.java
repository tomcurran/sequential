package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class Converter {
	
	private static String ten = "LIGHTBLUE";
	private static String twenty = "LIGHTYELLOW";
	private static String thirty = "YELLOW";
	private static String forty = "YELLOWGREEN";
	private static String fifty = "OLIVE";
	private static String sixty = "DARKGREEN";
	private static String seventy = "ORANGE";
	private static String eighty = "RED";
	private static String ninty = "INDIGO";
	private static String oneHundred= "BLUE";
	
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
				colour = ten;
			} else if (timePercentage <= 0.2) {
				colour = twenty;
			} else if (timePercentage <= 0.3) {
				colour = thirty;
			} else if (timePercentage <= 0.4) {
				colour = forty;
			} else if (timePercentage <= 0.5) {
				colour = fifty;
			} else if (timePercentage <= 0.6) {
				colour = sixty;
			} else if (timePercentage <= 0.7) {
				colour = seventy;
			} else if (timePercentage <= 0.8) {
				colour = eighty;
			} else if (timePercentage <= 0.9) {
				colour = ninty;
			} else if (timePercentage <= 1) {
				colour = oneHundred;
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
		
		String colours = "<size:18><back:"+ ten +"><color:"+ ten + "><b><u>"+ ten +"</u></b></color></back> 0-10%</size>\n";
		colours += "<size:18><back:"+ twenty +"><color:"+ twenty + "><b><u>"+ twenty +"</u></b></color></back> 11-20%</size>\n";
		colours += "<size:18><back:"+ thirty +"><color:"+ thirty + "><b><u>"+ thirty +"</u></b></color></back> 21-30%</size>\n";
		colours += "<size:18><back:"+ forty +"><color:"+ forty + "><b><u>"+ forty +"</u></b></color></back> 31-40%</size>\n";
		colours += "<size:18><back:"+ fifty +"><color:"+ fifty + "><b><u>"+ fifty +"</u></b></color></back> 41-50%</size>\n";
		colours += "<size:18><back:"+ sixty +"><color:"+ sixty + "><b><u>"+ sixty +"</u></b></color></back> 51-60%</size>\n";
		colours += "<size:18><back:"+ seventy +"><color:"+ seventy + "><b><u>"+ seventy +"</u></b></color></back> 61-70%</size>\n";
		colours += "<size:18><back:"+ eighty +"><color:"+ eighty + "><b><u>"+ eighty +"</u></b></color></back> 71-80%</size>\n";
		colours += "<size:18><back:"+ ninty +"><color:"+ ninty + "><b><u>"+ ninty +"</u></b></color></back> 81-90%</size>\n";
		colours += "<size:18><back:"+ oneHundred +"><color:"+ oneHundred + "><b><u>"+ oneHundred +"</u></b></color></back> 91-100%</size>\n";
		
		String retString = "@startuml\nactor User\nlegend\n<size:24><b>Time Exicuting</b></size>\n"+ colours +"\nendlegend\n";

		for(String val: timeline.values()) {
			retString += val;
		}
		
		retString += "@enduml\n";
		
		return retString;
	}
	

}
