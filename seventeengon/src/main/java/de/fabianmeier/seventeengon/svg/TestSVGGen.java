// package de.fabianmeier.seventeengon.svg;
// import java.awt.Color;
// import java.awt.Graphics2D;
// import java.awt.Rectangle;
// import java.io.File;
// import java.io.IOException;
// import java.util.HashSet;
// import java.util.Set;
//
// import de.fabianmeier.seventeengon.shapes.Circle;
// import de.fabianmeier.seventeengon.shapes.Gshape;
// import de.fabianmeier.seventeengon.shapes.GshapeImpl;
// import de.fabianmeier.seventeengon.shapes.Line;
// import de.fabianmeier.seventeengon.shapes.Pshape;
// import de.fabianmeier.seventeengon.shapes.Triangle;
// import de.fabianmeier.seventeengon.shapes.XYpoint;
//
// public class TestSVGGen
// {
//
// public void paint(Graphics2D g2d)
// {
// g2d.setPaint(Color.red);
// g2d.fill(new Rectangle(10, 10, 100, 100));
// }
//
// public static void main(String[] args) throws IOException
// {
// Triangle triangle = new Triangle(new XYpoint(30, 30),
// new XYpoint(100, 50), new XYpoint(10, 100), 1, "Hund");
//
// Circle circle = new Circle(new XYpoint(60, 54), 50, 2, "Ci");
//
// Line line = new Line(new XYpoint(0, 10), new XYpoint(30, 60));
//
// Set<Pshape> cut = circle.intersectWith(triangle);
//
// Set<Pshape> cut2 = line.intersectWith(triangle);
//
// Set<Pshape> circtri = new HashSet<Pshape>();
//
// circtri.add(triangle);
// circtri.add(circle);
// circtri.addAll(line.intersectWith(new Triangle(new XYpoint(0, 0),
// new XYpoint(10000, 0), new XYpoint(0, 10000), 0, null)));
//
// Gshape citri = new GshapeImpl(circtri, 1, "c");
// Gshape cutg = new GshapeImpl(cut, 2, "k");
// Gshape cutg2 = new GshapeImpl(cut2, 2, "k");
//
// Set<Gshape> gshapes = new HashSet<Gshape>();
//
// gshapes.add(citri);
// gshapes.add(cutg);
// gshapes.add(cutg2);
//
// ToSVG.writeToSVGFile(new File("C:\\temp\\tri.svg"), gshapes);
//
// }
//
// // // Get a DOMImplementation.
// // DOMImplementation domImpl = GenericDOMImplementation
// // .getDOMImplementation();
// //
// // // Create an instance of org.w3c.dom.Document.
// // String svgNS = "http://www.w3.org/2000/svg";
// // Document document = domImpl.createDocument(svgNS, "svg", null);
// //
// // // Create an instance of the SVG Generator.
// // SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
// //
// // // Ask the test to render into the SVG Graphics2D implementation.
// // TestSVGGen test = new TestSVGGen();
// // test.paint(svgGenerator);
// //
// // // Finally, stream out SVG to the standard output using
// // // UTF-8 encoding.
// // boolean useCSS = true; // we want to use CSS style attributes
// // Writer out = new OutputStreamWriter(
// // new FileOutputStream(new File("C:\\temp\\gut.svg")), "UTF-8");
// // svgGenerator.stream(out, useCSS);
// }