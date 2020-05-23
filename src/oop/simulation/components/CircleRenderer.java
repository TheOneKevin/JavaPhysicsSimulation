package oop.simulation.components;

import oop.simulation.math.Circle;
/**
 * This class generates the image of the circle on to the screen
 *
 * @author Nathan Ngo
 * @version April 2020
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
