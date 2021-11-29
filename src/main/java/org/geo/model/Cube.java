package org.geo.model;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Cube implements ThreeDimensionalShape{
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public Supplier<Double> getVolume() {
        return () -> Math.pow(side, 3);
    }

    @Override
    public Supplier<Double> getSurfaceArea() {
        return () -> 6 * Math.pow(side, 2);
    }
    public static Function<Map<String, String>, Shape> getShapeObjectFromParams() {
        return params -> new Cube(Double.parseDouble(params.get("side")));
    }
}
