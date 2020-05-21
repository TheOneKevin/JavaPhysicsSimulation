package oop.simulation.components;

import oop.simulation.math.Circle;
/*
    @author Nathan Ngo
 */
public class CircleRenderer extends BasicComponent
{
    private Circle circle;

    public CircleRenderer(Circle c)
    {
        circle = c;
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    public Circle getCircle()
    {
        return circle;
    }

    public void setCircle(Circle c)
    {
        circle = c;
    }
}
