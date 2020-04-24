package oop.simulation.components;

import oop.simulation.math.Polygon;

/**
 * Allows the polygon to be seen.
 *
 * @author Kevin Dai
 * @author Mustafa M.
 */
public class PolygonRenderer extends BasicComponent
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
