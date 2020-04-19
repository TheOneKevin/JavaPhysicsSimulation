package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.math.Polygon;

/**
 * Allows the polygon to be seen.
 *
 * @author Kevin Dai
 * @author Mustafa M.
 */
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

    @Override
    public void update(GameObject g)
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
}
