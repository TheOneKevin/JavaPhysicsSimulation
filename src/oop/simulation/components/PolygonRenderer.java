package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.math.Polygon;

public class PolygonRenderer implements IComponent
{
    private Polygon polygon;

    public PolygonRenderer(Polygon p)
    {
        polygon = p;
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    public Polygon getPolygon()
    {
        return polygon;
    }

    public void setPolygon(Polygon p)
    {
        polygon = p;
    }
}
