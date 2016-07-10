package de.fabianmeier.seventeengon.shapes;

import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;

/**
 * A drawable object in the plane, which can consist of combinations of atomic
 * objects. It has no drawable name by itself.
 * 
 * @author jfabi
 *
 */
public interface GeoObject
{
	/**
	 * Determines whether a given point lies inside the object.
	 * 
	 * @param point
	 *            An XYpoint
	 * @return if the point lies in the object
	 */
	boolean containsPoint(XYpoint point);

	/**
	 * 
	 * @return The object under deletion of all inner parts, i.e. it leaves the
	 *         boundaries of the circles and triangles and all other objects.
	 */
	GeoObject getBoundary();

	/**
	 * 
	 * @return The dimension of the maximal sub object (or the object itself).
	 *         -1 if the object is empty.
	 */
	int getDimension();

	/**
	 * 
	 * @return The object under filling of triangles and circles.
	 */
	GeoObject getFilledObject();

	/**
	 * Samples a point from the object.
	 * 
	 * @param sampleNumber
	 *            A number which gives a unique reference for the sampling
	 *            point.
	 * @return A point from the object or null if no such point exists.
	 */
	XYpoint getSamplePoint(int sampleNumber);

	/**
	 * 
	 * @return The list of subobjects. Can be empty, in which case the object is
	 *         either completely empty or an atomic object.
	 */
	List<GeoObject> getSubObjects();

	/**
	 * 
	 * @param other
	 *            Another GeoObject
	 * @return The intersection of both objects, as GeoObject
	 */
	GeoObject intersectWith(GeoObject other);

	/**
	 * 
	 * @return If the object contains no points at all
	 */
	boolean isEmpty();

	/**
	 * 
	 * @return All zero-dimensional parts, combined as point set
	 */
	Set<XYpoint> getZeroDimensionalPart();

	/**
	 * 
	 * @param preMap
	 *            a map of the plane
	 * @return the object changed by the given map
	 */
	GeoObject preservingMap(PreservingMap preMap);

	/**
	 * 
	 * 
	 * @return A set of angles in which the name can be drawn.
	 */
	List<Angle> getNameDrawingAngles();

	/**
	 * 
	 * @return Eliminates unnecessary levels of composition, unnecessery
	 *         zero-dimensional objects and empty subobjects
	 */
	GeoObject normalize();

	/**
	 * @param geo
	 *            a GeoObject
	 * @return the distance to this object
	 */
	double distanceTo(GeoObject geo);

}
