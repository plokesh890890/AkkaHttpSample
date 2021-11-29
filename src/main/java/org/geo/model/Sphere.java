package org.geo.model;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Sphere implements ThreeDimensionalShape{
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Supplier<Double> getVolume() {
        return () -> (4.0/3.0) * Math.PI * Math.pow(radius, 3);
    }

    @Override
    public Supplier<Double> getSurfaceArea() {
        return () -> 4 * Math.PI * Math.pow(radius, 2);
    }

    public static Function<Map<String, String>, Shape> getShapeObjectFromParams() {
        return params -> new Sphere(Double.parseDouble(params.get("radius")));
    }
}
