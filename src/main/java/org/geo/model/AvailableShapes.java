package org.geo.model;

import java.util.Map;
import java.util.function.Function;

public enum AvailableShapes {
    CIRCLE("circle", Circle.class, Circle.getShapeObjectFromParams()),
    TRIANGLE("triangle", Triangle.class, Triangle.getShapeObjectFromParams()),
    SPHERE("sphere", Sphere.class, Sphere.getShapeObjectFromParams()),
    CUBE("cube", Cube.class, Cube.getShapeObjectFromParams());

    private String name;
    private Class shape;
    private Function<Map<String, String>, Shape> shapeFactory;

    private AvailableShapes(String name, Class shape,  Function<Map<String, String>, Shape> shapeFactory) {
        this.name = name;
        this.shape = shape;
        this.shapeFactory = shapeFactory;
    }

    public String getName() {
        return this.name;
    }

    public Class getShape() {
        return this.shape;
    }

    public Function<Map<String, String>, Shape> getShapeFactory() {
        return shapeFactory;
    }

    public static Function<Map<String, String>, Shape> getShapeFactory(String name) {
        for (AvailableShapes shape : AvailableShapes.values()) {
            if (shape.getName().equals(name)) {
                return shape.getShapeFactory();
            }
        }
        throw new IllegalArgumentException("No shape with name " + name);
    }
}
