package oop.simulation.math;
/*
    @author Nathan Ngo
 */

public class Circle
{
    private double density;
    private double PI = 3.14;
    private double radius;
    private double mass = density  * PI * radius;

    public Circle(double theDensity, double theRadius)
    {
        density = theDensity;
        radius = theRadius;
    }

    public double getMomentOfInertia()
    {
        double value = 0.25 * mass * radius * radius;
        return value;
    }
}
