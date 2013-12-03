package model;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Converter {

	private static final String PERCENTAGE_10 = "LightBlue";
	private static final String PERCENTAGE_20 = "LightYellow";
	private static final String PERCENTAGE_30 = "Yellow";
	private static final String PERCENTAGE_40 = "YellowGreen";
	private static final String PERCENTAGE_50 = "Olive";
	private static final String PERCENTAGE_60 = "DarkGreen";
	private static final String PERCENTAGE_70 = "Orange";
	private static final String PERCENTAGE_80 = "Red";
	private static final String PERCENTAGE_90 = "Indigo";
	private static final String PERCENTAGE_100 = "Blue";

	private static final String TEMPLATE_UML = "@startuml\nactor User\n%s%s@enduml\n";
	private static final String TEMPLATE_OBJECT = "%s.%s";
	private static final String TEMPLATE_METHOD_START = "%1$s -> %2$s: %3$s()\nactivate %2$s #%4$s\n";
	private static final String TEMPLATE_METHOD_END = "%2$s --> %1$s: \ndeactivate %2$s\n";
//	private static final String TEMPLATE_METHOD_END_LENGTH = "||$3$s||\n" + TEMPLATE_METHOD_END;
	private static final String TEMPLATE_LEGEND = "legend\n<size:24><b>Time Executing</b></size>\n%s\nendlegend\n";
	private static final String TEMPLATE_TIME = "<size:18>%2$s%% <back:%1$s><color:%1$s><b><u>%1$s</u></b></color></back></size>\n";

	public static String toPlantUML(List<MethodCall> methodsCalled) throws Exception {

		SortedMap<Integer, String> timeline = new TreeMap<Integer, String>();

		double totalDuration = 0.0;
		for (MethodCall m : methodsCalled) {
			totalDuration += (double) m.getDuration();
		}

		for (MethodCall m : methodsCalled) {
			String name = String.format(TEMPLATE_OBJECT, m.getObject().getObjectName(), m.getObject().getClassName());
			String name2 = m.getInvokingObject() != null ? String.format(TEMPLATE_OBJECT, m.getInvokingObject().getObjectName(), m.getInvokingObject().getClassName()) : "User";
//			String length = String.valueOf((m.completedAt() - m.calledAt())/100000);
			String colour;
			double timePercentage = (double) m.getDuration() / totalDuration;

			if (timePercentage <= 0.1) {
				colour = PERCENTAGE_10;
			} else if (timePercentage <= 0.2) {
				colour = PERCENTAGE_20;
			} else if (timePercentage <= 0.3) {
				colour = PERCENTAGE_30;
			} else if (timePercentage <= 0.4) {
				colour = PERCENTAGE_40;
			} else if (timePercentage <= 0.5) {
				colour = PERCENTAGE_50;
			} else if (timePercentage <= 0.6) {
				colour = PERCENTAGE_60;
			} else if (timePercentage <= 0.7) {
				colour = PERCENTAGE_70;
			} else if (timePercentage <= 0.8) {
				colour = PERCENTAGE_80;
			} else if (timePercentage <= 0.9) {
				colour = PERCENTAGE_90;
			} else if (timePercentage <= 1) {
				colour = PERCENTAGE_100;
			} else {
				throw new Exception("Invalid percentage");
			}

			timeline.put(m.getStartId(), String.format(TEMPLATE_METHOD_START, name2, name, m.getMethodName(), colour));
			timeline.put(m.getEndId(), String.format(TEMPLATE_METHOD_END, name2, name));
//			timeline.put(m.stopId(), String.format(TEMPLATE_METHOD_END_LENGTH, name2, name, length));
		}

		String colours = "";
		colours  = String.format(TEMPLATE_TIME, PERCENTAGE_10, " 0-10");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_20, "11-20");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_30, "21-30");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_40, "31-40");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_50, "41-50");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_60, "51-60");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_70, "61-70");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_80, "71-80");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_90, "81-90");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_100, "91-100");

		String legend = String.format(TEMPLATE_LEGEND, colours);

		String methods = "";
		for (String val : timeline.values()) {
			methods += val;
		}

		return String.format(TEMPLATE_UML, methods, legend);
	}

}