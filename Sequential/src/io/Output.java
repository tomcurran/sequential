package io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import model.Converter;
import model.MethodCall;

public class Output {

	public static void writeSVG(String input, String output) throws Exception {
		List<MethodCall> methodCalls = Converter.parseFile(input);
		String data = Converter.toPlantUML(methodCalls);

		SourceStringReader reader = new SourceStringReader(data);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
		os.close();

		final String svg = new String(os.toByteArray());

		File outputFile = new File(output);
		outputFile.createNewFile();
		outputFile.getParentFile();
		outputFile.mkdirs();

		PrintWriter writer = new PrintWriter(outputFile.getPath(), "UTF-8");
		writer.println(svg);
		writer.close();
	}

}
