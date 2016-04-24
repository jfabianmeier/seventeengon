/// **
// *
// */
// package de.fabianmeier.seventeengon.geoobjects;
//
/// **
// * @author JFM
// *
// */
// public class GeoTriangle extends GeoDrawingObject
// {
//
// private GeoPoint c;
// private GeoPoint b;
// private GeoPoint a;
//
// public GeoTriangle(GeoPoint a, GeoPoint b, GeoPoint c)
// {
// this.a = a;
// this.b = b;
// this.c = c;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see
// * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
// * seventeengon.geoobjects.GeoCanvas, java.lang.String)
// */
// @Override
// public void draw(GeoCanvas canvas, String label)
// {
// canvas.drawLine(a.getXY(), b.getXY(), getVisibility(), label);
// canvas.drawLine(b.getXY(), c.getXY(), getVisibility(), null);
// canvas.drawLine(c.getXY(), a.getXY(), getVisibility(), null);
// }
//
// }
