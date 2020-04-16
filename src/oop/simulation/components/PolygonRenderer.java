package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;

public class PolygonRenderer implements IComponent
{
    private Polygon polygon;
    private GameObject parentObj;

    public PolygonRenderer(Polygon p)
    {
        polygon = p;
    }

    @Override
    public GameObject parent()
    {
        return parentObj;
    }

    @Override
    public void addedToParent(GameObject o)
    {
        parentObj = o;
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

    @Override
    public void update()
    {

    }
}
