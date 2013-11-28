package view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import model.Converter;
import model.test.TestData;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLTest {

	public static void main(String[] args) throws IOException {
		String source = Converter.toPlantUML(TestData.get());
				
//				

		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
		os.close();

		final String svg = new String(os.toByteArray());
		
		PrintWriter writer = new PrintWriter("tmp"+ File.separator +"test.svg", "UTF-8");
		writer.println(svg);
		writer.close();
	}

}
