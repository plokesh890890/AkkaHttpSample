package org.geo.model;

import java.util.function.Supplier;

public interface TwoDimensionalShape extends Shape {
    Supplier<Double> getArea();
    Supplier<Double> getPerimeter();

    @Override
    default boolean isTwoDimensional() {
        return true;
    }

    @Override
    default boolean isThreeDimensional() {
        return false;
    }
}
