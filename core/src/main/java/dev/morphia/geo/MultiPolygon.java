package dev.morphia.geo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a set of polygons, which will saved into MongoDB as per the <a href="http://geojson.org/geojson-spec
 * .html#id7">GeoJSON
 * specification</a>.
 * <p/>
 * The factory for creating a MultiPolygon is the {@code GeoJson.multiPolygon} method.
 *
 * @see dev.morphia.geo.GeoJson#multiPolygon(Polygon...)
 * @deprecated use the driver-provided types instead
 */
@SuppressWarnings("removal")
@Deprecated(since = "2.0", forRemoval = true)
public class MultiPolygon implements Geometry {
    private final List<Polygon> coordinates;

    @SuppressWarnings("UnusedDeclaration") // used by Morphia
    private MultiPolygon() {
        coordinates = new ArrayList<>();
    }

    MultiPolygon(Polygon... polygons) {
        coordinates = Arrays.asList(polygons);
    }

    MultiPolygon(List<Polygon> polygons) {
        coordinates = polygons;
    }

    @Override
    public List<Polygon> getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    /* equals, hashCode and toString. Useful primarily for testing and debugging. Don't forget to re-create when changing this class */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiPolygon that = (MultiPolygon) o;

        return coordinates.equals(that.coordinates);
    }

    @Override
    public String toString() {
        return "MultiPolygon{"
                + "coordinates=" + coordinates
                + '}';
    }

}
