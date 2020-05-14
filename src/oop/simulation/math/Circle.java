package oop.simulation.math;
/*
    @author Nathan Ngo
 */

public class Circle
{
    private double density;
    private final double PI = 3.14;
    private double radius;
    private double mass = density  * PI * radius;

    public Circle(double theDensity, double theRadius)
    {
        density = theDensity;
        radius = theRadius;
    }
    /*
        This is used to fin the moment of inertia in a circle. It is finding the torque needed for
        angular acceleration that is desired by the user based on the circle's mass and radius.
     */
    public double getMomentOfInertia()
    {
        double value = 0.25 * mass * radius * radius;
        return value;
    }

    public double getArea()
    {
        return PI * radius * radius;
    }

    public double getDiameter()
    {
        return radius * 2;
    }

    public double getCircumference()
    {
        return 2 * PI * radius;
    }
}
