package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Converter {

	public static String toPlantUML(List<MethodCall> methodsCalled) throws Exception {

		final String PERCENTAGE_10 = "LightBlue";
		final String PERCENTAGE_20 = "LightYellow";
		final String PERCENTAGE_30 = "Yellow";
		final String PERCENTAGE_40 = "YellowGreen";
		final String PERCENTAGE_60 = "DarkGreen";
		final String PERCENTAGE_50 = "Olive";
		final String PERCENTAGE_70 = "Orange";
		final String PERCENTAGE_80 = "Red";
		final String PERCENTAGE_90 = "Indigo";
		final String PERCENTAGE_100 = "Blue";

		final String TEMPLATE_UML = "@startuml\nactor User\n%s%s@enduml\n";
		final String TEMPLATE_OBJECT = "%s";
		final String TEMPLATE_METHOD_START = "%1$s -> %2$s: %3$s()\nactivate %2$s #%4$s\n";
		final String TEMPLATE_METHOD_END = "%2$s --> %1$s: %3$ss \ndeactivate %2$s\n";
		final String TEMPLATE_LEGEND = "legend\n<size:24><b>Time Executing</b></size>\n%s\nendlegend\n";
		final String TEMPLATE_TIME = "<size:18>%2$s%% <back:%1$s><color:%1$s><b>################</b></color></back></size>\n";

		SortedMap<Integer, String> timeline = new TreeMap<Integer, String>();

		double totalDuration = 0.0;
		for (MethodCall m : methodsCalled) {
			totalDuration += (double) m.getDuration();
		}

		for (MethodCall m : methodsCalled) {
			String name = String.format(TEMPLATE_OBJECT, m.getExicutingClass());
			String name2 = m.getInvokingClass() != null ? String.format(TEMPLATE_OBJECT, m.getInvokingClass()) : "User";
			String colour;
			long duration = m.getDuration();
			double durationSeconds = (double) duration / 1000000000.0;
			double timePercentage = (double) duration / totalDuration;

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
			timeline.put(m.getEndId(), String.format(TEMPLATE_METHOD_END, name2, name, durationSeconds));
		}

		String colours = "";
		colours = String.format(TEMPLATE_TIME, PERCENTAGE_10, "  0-10");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_20, " 11-20");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_30, " 21-30");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_40, " 31-40");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_50, " 41-50");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_60, " 51-60");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_70, " 61-70");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_80, " 71-80");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_90, " 81-90");
		colours += String.format(TEMPLATE_TIME, PERCENTAGE_100, "91-100");

		String legend = String.format(TEMPLATE_LEGEND, colours);

		String methods = "";
		for (String val : timeline.values()) {
			methods += val;
		}

		return String.format(TEMPLATE_UML, methods, legend);
	}

	public static List<MethodCall> parseFile(String inputFileName) throws IOException {
		Path inputFile = Paths.get(inputFileName);
		List<String> rawCalls = Files.readAllLines(inputFile, StandardCharsets.UTF_8);
		List<MethodCall> methodCalls = new ArrayList<MethodCall>();

		for (String rawCall : rawCalls) {

			String[] callData = rawCall.split(",");
			String className = callData[0].replace('/', '.');

			switch (callData[2]) {
			case ">": {
				String calledFrom = null;
				for (int i = methodCalls.size() - 1; i >= 0; i--) {
					MethodCall j = methodCalls.get(i);
					if (j.endId < 0) {
						calledFrom = j.getExicutingClass();
						break;
					}
				}

				MethodCall newCall = new MethodCall(callData[1], calledFrom, className);
				newCall.startId = MethodCall.getId();
				newCall.startTime = Long.parseLong(callData[3]);
				methodCalls.add(newCall);
				break;
			}
			case "<": {
				for (int i = methodCalls.size() - 1; i >= 0; i--) {
					MethodCall j = methodCalls.get(i);
					if (j.endId < 0 && j.getMethodName().equals(callData[1]) && j.getExicutingClass().endsWith(className)) {
						j.endId = MethodCall.getId();
						j.endTime = Long.parseLong(callData[3]);
						j.duration = j.endTime - j.startTime;
						break;
					}
				}
				break;
			}
			}

		}

		return methodCalls;
	}
}