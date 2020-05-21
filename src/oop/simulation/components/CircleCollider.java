package oop.simulation.components;

import oop.simulation.math.Circle;
import oop.simulation.math.MatN;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.physics2d.IShape;

/*
    @author Nathan Ngo
 */

public class CircleCollider extends BasicComponent implements IShape
{
    private Circle c;

    // Cached
    private Vec2 centroidWorld;
    private MatN T;

    @Override
    public Vec2 getSupport(Vec2 in) {
        return null;
    }

    public CircleCollider(Circle collider)
    {
        this.c = collider;
    }

    @Override
    public void update()
    {
        T = Transform.computeModelWorldMatrix(owner);
        centroidWorld = Vec2.wTransform(T, new Vec2(0, 0));
    }

    @Override
    public Vec2 getSupportWorld(Vec2 in) {
        // let rv = r * (v / ||v||)
        Vec2 rv = Vec2.scalarMultiply(Vec2.normalize(in), c.getRadius());
        // support = c + rv
        return Vec2.add(getCentroidWorld(), rv);
    }

    @Override
    public Vec2 getCentroidWorld() {
        return centroidWorld;
    }
    
    @Override
    public double getMomentOfInertia() {
        return c.getMomentOfInertia();
    }
}
