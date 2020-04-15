package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.math.Vec2;

import java.util.ArrayList;

public class PolygonRenderer implements IComponent
{
    private Polygon polygon;

    public PolygonRenderer(Polygon p)
    {
        polygon = p;
    }

    @Override
    public GameObject parent()
    {
        return null;
    }

    @Override
    public void addedToParent(GameObject o)
    {

    }

    public Polygon getPolygon()
    {
        return polygon;
    }
    public void setPolygon(Polygon p)
    {
        polygon = p;
    }

    @Override
    public boolean isUnique()
    {
        return false;
    }
}
