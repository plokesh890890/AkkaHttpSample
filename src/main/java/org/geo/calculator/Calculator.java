package org.geo.calculator;

import org.geo.model.ThreeDimensionalShape;
import org.geo.model.TwoDimensionalShape;

import java.util.function.Function;

public interface Calculator {

    static Function<TwoDimensionalShape, Double> calculateArea = shape -> shape.getArea().get();

    static Function<TwoDimensionalShape, Double> calculatePerimeter = shape -> shape.getPerimeter().get();

    static Function<ThreeDimensionalShape, Double> calculateVolume = shape -> shape.getVolume().get();

    static Function<ThreeDimensionalShape, Double> calculateSurfaceArea = shape -> shape.getSurfaceArea().get();

}
