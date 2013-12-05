package view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import model.Converter;
import model.MethodCall;
import model.test.TestData;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLTest {
	
	public static void WriteSVG(String input, String output) throws Exception {
			
		List<MethodCall> methodCalls = Converter.ParseFile(input);
		String data = Converter.toPlantUML(methodCalls);
				
		SourceStringReader reader = new SourceStringReader(data);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
		os.close();

		final String svg = new String(os.toByteArray());
		
		File outputFile = new File(output);
		outputFile.getParentFile().mkdirs();
		
		PrintWriter writer = new PrintWriter(outputFile.getPath(), "UTF-8");
		writer.println(svg);
		writer.close();
	}
}
