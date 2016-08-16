import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * This program generates datasets for the Gardener problem.
 * 
 * Use your favorite drawing program to draw a layout using only
 * lines. Keep the program's "snap" options on so that the endpoints
 * have nice values.  Export your drawing as svg. 
 * 
 *  This program reads an svg file looking for <line ...>
 *  elements and interpret each such element as an edge
 *  in the dataset.
 * 
 * This has been tested with the dia drawing program and its "simple SVG"
 * export, but will probably work with a wide variety of svg exporters.
 *  
 * @author zeil
 *
 */
public class DataGenerator {

	private PrintStream out;
	private File inputFile;
	private HashMap<Point, Integer> pointNumbers;
	private ArrayList<Edge> edges;
	private ArrayList<Point> points;


	public DataGenerator(File inputSVG, File outputData) throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(outputData));
		inputFile = inputSVG;
		pointNumbers = new HashMap<DataGenerator.Point, Integer>();
		edges = new ArrayList<DataGenerator.Edge>();
		points = new ArrayList<DataGenerator.Point>();
	}

	class Point {
		int x;
		int y;

		Point (int xx, int yy) {
			x = xx;
			y = yy;
		}

		public boolean equals (Object obj) {
			Point p = (Point)obj;
			return x == p.x && y == p.y;
		}

		public int hashCode() {
			return x + 131 * y;
		}

		public String toString ()
		{
			return x + " " + y;
		}

	}



	class Edge {
		int from;
		int to;

		Edge (int xx, int yy) {
			from = xx;
			to = yy;
		}

		public boolean equals (Object obj) {
			Edge p = (Edge)obj;
			return from == p.from && to == p.to;
		}

		public int hashCode() {
			return from + 131 * to;
		}

		public String toString ()
		{
			return from + " " + to;
		}

	}



	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File outputData;
		File inputSVG;
		if (args.length == 0) {
			File currentDir = new File(".");
			final JFileChooser inputSelector = new JFileChooser(currentDir);
			inputSelector.setDialogTitle("Choose SVG file for input");
			inputSelector.addChoosableFileFilter(
					new FileFilter() {

						@Override
						public boolean accept(File file0) {
							String extension = file0.getName();
							int k = extension.lastIndexOf('.');
							if (k < 0)
								return false;
							extension = extension.substring(k+1);
							return extension.equals("svg");
						}

						@Override
						public String getDescription() {
							return "SVG files";
						}

					}
					);
			inputSelector.setAcceptAllFileFilterUsed(false);
			int returnval = inputSelector.showOpenDialog(null);
			if (returnval != JFileChooser.APPROVE_OPTION) {
				System.exit(1);
			}
			inputSVG = inputSelector.getSelectedFile();

			final JFileChooser outputSelector = new JFileChooser(currentDir);
			outputSelector.setDialogTitle("Data file to hold output");
			returnval = outputSelector.showSaveDialog(null);
			if (returnval != JFileChooser.APPROVE_OPTION) {
				System.exit(1);
			}
			outputData = outputSelector.getSelectedFile();
		} else {
			inputSVG = new File(args[0]);
			outputData = new File(args[1]);
		}

		new DataGenerator(inputSVG, outputData).process();
	}

	class SAXHandler extends DefaultHandler {
		@Override
		public void startElement(String uri, String localName,
				String qName, Attributes attributes)
						throws SAXException {
			if (qName.equals("line")) {
				long x1 = Math.round(Double.parseDouble(attributes.getValue("x1")));
				long y1 = Math.round(Double.parseDouble(attributes.getValue("y1")));
				long x2 = Math.round(Double.parseDouble(attributes.getValue("x2")));
				long y2 = Math.round(Double.parseDouble(attributes.getValue("y2")));

				Point p1 = new Point((int)x1, (int)y1);
				Point p2 = new Point((int)x2, (int)y2);

				Integer from = pointNumbers.get(p1);
				if (from == null) {
					from = points.size();
					pointNumbers.put(p1, from);
					points.add(p1);
				}

				Integer to = pointNumbers.get(p2);
				if (to == null) {
					to = points.size();
					pointNumbers.put(p2, to);
					points.add(p2);
				}
				edges.add(new Edge(from, to));
			}
		}


	}

	private void process() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory parserFactor = SAXParserFactory.newInstance();
		SAXParser parser = parserFactor.newSAXParser();
		SAXHandler handler = new SAXHandler();
		parser.parse(inputFile,handler);

		out.println(points.size() + " " + edges.size());
		System.err.println(points.size() + " " + edges.size());
		for (Point p: points) {
			out.println(p);
			System.err.println(p);
		}
		for (Edge e: edges) {
			out.println(e);
			System.err.println(e);			
		}
		out.println ("0 0");
		out.close();
	}

}
