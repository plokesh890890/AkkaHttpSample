package org.geo.model;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Circle implements TwoDimensionalShape {
    private double radius;

    public Circle(Builder builder) {
        this.radius = builder.radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public static Function<Map<String, String>, Shape> getShapeObjectFromParams() {
        return params -> new Builder().radius(Double.parseDouble(params.get("radius"))).build();
    }

    public static class Builder {

        private double radius;

        public Builder() {
        }

        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public Circle build() {
            return new Circle(this);
        }
    }

    @Override
    public Supplier<Double> getArea() {
        return () -> Math.PI * Math.pow(radius, 2);
    }

    @Override
    public Supplier<Double> getPerimeter() {
        return () -> 2 * Math.PI * radius;
    }
}
