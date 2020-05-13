package oop.simulation.components;

import oop.simulation.math.Circle;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.physics2d.IShape;

public class CircleCollider extends BasicComponent implements IShape
{
    private oop.simulation.math.Circle Circle;

    @Override
    public Vec2 getSupport(Vec2 in) {
        return null;
    }

    public CircleCollider(Circle collider)
    {
        this.Circle = collider;
    }

    @Override
    public Vec2 getSupportWorld(Vec2 in) {
        return null;
    }

    @Override
    public Vec2 getCentroidWorld() {
        return null;
    }
    
    @Override
    public double getMomentOfInertia() {
        return Circle.getMomentOfInertia();
    }
}
