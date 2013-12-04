package view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;

import model.Converter;
import model.test.TestData;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLTest {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Missing arguments, please provide output file name");
			return;
		}
		
		final String fileName = args[0] + ".svg";	
		final String folder = "output";
		
		String source = Converter.toPlantUML(TestData.get());
				
		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
		os.close();

		final String svg = new String(os.toByteArray());

		File outputFile = new File(folder);
		outputFile.mkdirs();
		PrintWriter writer = new PrintWriter(folder + File.separator + fileName, "UTF-8");
		writer.println(svg);
		writer.close();
	}

}
