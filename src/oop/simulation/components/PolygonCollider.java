package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.math.MatN;
import oop.simulation.math.Vec2;
import oop.simulation.math.Polygon;
import oop.simulation.physics2d.IShape;

/**
 * Enables collisions between colliders
 * It takes in the geometric data of the circles and determines how it will respond based
 * on the physics of the world.
 *
 * @author Kevin Dai
 */
public class PolygonCollider extends BasicComponent implements IShape
{
    private Polygon polygon;

    // Cached
    private Vec2 centroidWorld;
    private MatN T;

    public PolygonCollider(Polygon collider)
    {
        this.polygon = collider;
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    /**
     * Updates the circle and interacts with the transform class so that the circle can be
     * created.
     */
    @Override
    public void update()
    {
        T = Transform.computeModelWorldMatrix(owner);
        centroidWorld = Vec2.wTransform(T, new Vec2(0, 0));
    }

    /**
     * This maps out the circle to describe it in a different way then storing the
     * vertices.
     * @param in      Determines the coordinates needed in the 2d plane
     */
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

    /**
     * This maps out the circle to describe it in a different way then storing the
     * vertices.
     * @param in      Determines the coordinates needed in the 2d plane
     */
    @Override
    public Vec2 getSupportWorld(Vec2 in)
    {
        Vec2 out = new Vec2(0, 0);
        double highest = -Double.MAX_VALUE;
        for(Vec2 v : polygon.getVertices())
        {
            Vec2 w = Vec2.wTransform(T, v);
            double dot = Vec2.getEuclideanInnerProduct(w, in);
            if(dot > highest)
            {
                highest = dot;
                out.x.set(w.x.get());
                out.y.set(w.y.get());
            }
        }
        return out;
    }
    /**
     * Finds the centroid world of the circle. It can be located as the small circe
     * that is found at the center of every polygon and circle.
     */
    @Override
    public Vec2 getCentroidWorld()
    {
        return centroidWorld.clone();
    }

    /**
     * Finds the It is finding the torque needed for angular acceleration that is desired
     * by the user based on the circle's mass and radius.
     */
    @Override
    public double getMomentOfInertia()
    {
        return polygon.getMomentOfInertia();
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
