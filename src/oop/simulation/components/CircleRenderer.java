package oop.simulation.components;

import oop.simulation.math.Circle;
import oop.simulation.math.Polygon;

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

    public void setPolygon(Circle c)
    {
        circle = c;
    }
}
