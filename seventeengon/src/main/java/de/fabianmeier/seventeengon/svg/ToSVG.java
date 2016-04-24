// package de.fabianmeier.seventeengon.svg;
//
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.OutputStreamWriter;
// import java.io.UnsupportedEncodingException;
// import java.io.Writer;
// import java.util.Set;
//
// import org.apache.batik.dom.GenericDOMImplementation;
// import org.apache.batik.svggen.SVGGraphics2D;
// import org.apache.batik.svggen.SVGGraphics2DIOException;
// import org.w3c.dom.DOMImplementation;
// import org.w3c.dom.Document;
//
// import de.fabianmeier.seventeengon.shapes.Gshape;
// import de.fabianmeier.seventeengon.shapes.Pshape;
//
// public class ToSVG
// {
// public static void writeToSVGFile(File svgFile, Set<Gshape> gshapeSet)
// throws UnsupportedEncodingException, FileNotFoundException,
// SVGGraphics2DIOException
// {
// // Get a DOMImplementation.
// DOMImplementation domImpl = GenericDOMImplementation
// .getDOMImplementation();
//
// // Create an instance of org.w3c.dom.Document.
// String svgNS = "http://www.w3.org/2000/svg";
// Document document = domImpl.createDocument(svgNS, "svg", null);
//
// // Create an instance of the SVG Generator.
// SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
//
// // Ask the test to render into the SVG Graphics2D implementation.
// // TestSVGGen test = new TestSVGGen();
// // test.paint(svgGenerator);
//
// svgGenerator.scale(2, 2);
//
// for (int i = 0; i <= 2; i++)
// for (Gshape gshape : gshapeSet)
// for (Pshape pshape : gshape.getPrimitiveShapes())
// {
// if (pshape.getVisibility() == i)
// pshape.paint(svgGenerator);
// }
//
// // Finally, stream out SVG to the standard output using
// // UTF-8 encoding.
// boolean useCSS = true; // we want to use CSS style attributes
// Writer out = new OutputStreamWriter(new FileOutputStream(svgFile),
// "UTF-8");
// svgGenerator.stream(out, useCSS);
//
// }
//
// }
