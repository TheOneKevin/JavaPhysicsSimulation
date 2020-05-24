package oop.simulation.math;

import oop.simulation.physics2d.IShape;

/**
 * Various geometry-related static functions.
 *
 * @author Kevin Dai
 */
public class Geometry
{
    /**
     * Minkowski difference in world coordinates of 2 shapes given
     * a vector direction to search in.
     * @param shape1 Shape 1
     * @param shape2 Shape 2
     * @param d      Direction vector
     * @return Difference
     */
    public static Vec2 getMinkowskiDifference(IShape shape1, IShape shape2, Vec2 d)
    {
        return Vec2.subtract(shape2.getSupportWorld(d), shape1.getSupportWorld(Vec2.negate(d)));
    }

    /**
     * Checks if the triangle bounded by 3 vertices contains the origin.
     * @param a Vertex 1
     * @param b Vertex 2
     * @param c Vertex 3
     * @return True if the origin is contained within the triangle.
     */
    public static boolean originInTriangle(Vec2 a, Vec2 b, Vec2 c)
    {
        // TODO: Profile. Then if needed, optimize further -> remove vector operations.
        Vec2 na = Vec2.negate(a);
        Vec2 nb = Vec2.negate(b);
        boolean s_ba = Vec2.subtract(b, a).cross(na) > 0;
        if(Vec2.subtract(c, a).cross(na) > 0 == s_ba) return false;
        if(Vec2.subtract(c, b).cross(nb) > 0 != s_ba) return false;
        return true;
    }
}
