package oop.simulation.math;

import oop.simulation.physics2d.collision.IShape;

/**
 * Various geometry-related static functions
 */
public class Geometry
{
    public static Vec2 getMinkowskiDifference(IShape shape1, IShape shape2, Vec2 d)
    {
        return shape1.getSupport(d).subtract(shape2.getSupport(Vec2.negate(d)));
    }

    public static boolean originInTriangle(Vec2 a, Vec2 b, Vec2 c)
    {
        // TODO: Profile. Then if needed, optimize further -> remove vector operations.

        Vec2 ba = Vec2.subtract(b, a);
        Vec2 ca = Vec2.subtract(c, a);
        Vec2 cb = Vec2.subtract(c, b);
        Vec2 na = Vec2.negate(a);
        Vec2 nb = Vec2.negate(b);

        boolean s_na = ba.cross(na) > 0;
        if(ca.cross(na) > 0 == s_na) return true;
        if(cb.cross(nb) > 0 != s_na) return false;
        return true;
    }
}
