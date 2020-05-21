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
    private double radius;
    private ArrayList<Vec2> Circumference = new ArrayList<>();

    public Circle(double theRadius)
    {
        radius = theRadius;
    }

    /*
        This is used to fin the moment of inertia in a circle. It is finding the torque needed for
        angular acceleration that is desired by the user based on the circle's mass and radius.
     */

    public double getMomentOfInertia()
    {
        return 0.25 * radius * radius;
    }

    public double getArea()
    {
        return Math.PI * radius * radius;
    }

    public double getRadius()
    {
        return radius;
    }

    public double getCircumference()
    {
        return 2 * Math.PI * radius;
    }
}
