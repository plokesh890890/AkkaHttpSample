package org.geo.model;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Triangle implements TwoDimensionalShape{
    private double height;
    private double base;

    public Triangle(double height, double base) {
        this.height = height;
        this.base = base;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public Supplier<Double> getArea() {
        return () -> height * base / 2;
    }

    @Override
    public Supplier<Double> getPerimeter() {
        return () -> base + height + Math.sqrt(base * base + height * height);
    }

    public static Function<Map<String, String>, Shape> getShapeObjectFromParams() {
        return params -> new Triangle(Double.parseDouble(params.get("height")), Double.parseDouble(params.get("base")));
    }
}
