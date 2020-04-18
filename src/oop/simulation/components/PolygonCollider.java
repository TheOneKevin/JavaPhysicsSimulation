package oop.simulation.components;

import oop.simulation.IComponent;
import oop.simulation.math.Vec2;
import oop.simulation.math.Polygon;
import oop.simulation.physics2d.collision.IShape;

/**
 * Enables collisions between colliders
 *
 * @author Kevin Dai
 */
public class PolygonCollider implements IComponent, IShape
{
    private Polygon polygon;

    public PolygonCollider(Polygon collider)
    {
        this.polygon = collider;
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    @Override
    public Vec2 getSupport(Vec2 in)
    {
        Vec2 out = new Vec2(0, 0);
        double highest = 0;
        for(Vec2 v : polygon.getVertices())
        {
            double dot = Vec2.getEuclideanInnerProduct(v, in);
            if(dot > highest)
            {
                highest = dot;
                out.x.set(v.x.get());
                out.y.set(v.y.get());
            }
        }
        return out;
    }

    @Override
    public Vec2 getCentroid()
    {
        return polygon.getCentroid();
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
