package oop.simulation.components;

import oop.simulation.math.Circle;
import oop.simulation.math.MatN;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.physics2d.IShape;

/**
 * This class is the collision system for the circles.
 * It takes in the geometric data of the circles and determines how it will respond based
 * on the physics of the world.
 *
 * @author Nathan Ngo
 * @version April 2020
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
    public Vec2 getSupportWorld(Vec2 in) {
        // let rv = r * (v / ||v||)
        Vec2 rv = Vec2.scalarMultiply(Vec2.normalize(in), c.getRadius());
        // support = c + rv
        return Vec2.add(getCentroidWorld(), rv);
    }
    /**
     * Finds the centroid world of the circle. It can be located as the small circe
     * that is found at the center of every polygon and circle.
     */
    @Override
    public Vec2 getCentroidWorld() {
        return centroidWorld;
    }

    /**
     * Finds the It is finding the torque needed for angular acceleration that is desired
     * by the user based on the circle's mass and radius.
     */
    
    @Override
    public double getMomentOfInertia() {
        return c.getMomentOfInertia();
    }
}
