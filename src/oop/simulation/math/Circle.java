package oop.simulation.math;
import java.util.ArrayList;
import java.util.Collections;
/*
    @author Nathan Ngo
 */

import java.util.ArrayList;

public class Circle
{
    private double density;
    private final double PI = 3.14;
    private double radius;
    private double mass = density  * PI * radius;
    private Vec2 centroidWorld;
    private ArrayList<Vec2> Circumference = new ArrayList<>();

    public Circle(double theDensity, double theRadius, Vec2 theCentroidWorld)
    {
        density = theDensity;
        radius = theRadius;
        centroidWorld = theCentroidWorld;
    }
    /*
        This is used to fin the moment of inertia in a circle. It is finding the torque needed for
        angular acceleration that is desired by the user based on the circle's mass and radius.
     */

    public Vec2 getCentroidWorld()
    {
        return centroidWorld;
    }
    public double getMomentOfInertia()
    {
        double value = 0.25 * mass * radius * radius;
        return value;
    }

    public double getArea()
    {
        double area = PI * radius * radius;
        return area;
    }

    public double getDiameter()
    {
        double diameter = radius * 2;
        return diameter;
    }

    public double getCircumference()
    {
        double circumference = 2 * PI * radius;
        return circumference;
    }
}
