package oop.simulation.math;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Circle is an actor in greenfoot.
 * It stores the information required to construct a circle
 *
 * @author Nathan Ngo
 * @version April 2020
 */

import java.util.ArrayList;

public class Circle
{
    private double density;
    private double radius;
    //Stores information of all the points that lie on the circumference of the circle.
    private ArrayList<Vec2> Circumference = new ArrayList<>();

    /**
     * This is the constructor for the circle class,
     * @param theRadius       Determines the radius of the circle
     */
    public Circle(double theRadius)
    {
        radius = theRadius;
    }

    /**
     * This is used to find the moment of inertia in a circle. It is calculating the torque needed for
     * angular acceleration that is desired by the user based on the circle's mass and radius.
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
