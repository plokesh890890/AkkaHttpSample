package org.geo.model;

import java.util.function.Supplier;

public interface ThreeDimensionalShape extends Shape {

    public Supplier<Double> getVolume();
    public Supplier<Double> getSurfaceArea();

    @Override
    default boolean isTwoDimensional() {
        return false;
    }

    @Override
    default boolean isThreeDimensional() {
        return true;
    }
}
