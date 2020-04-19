package oop.simulation.physics2d.collision;

import oop.simulation.math.Geometry;
import oop.simulation.math.Vec2;

public class MprCollision
{
    private static Vec2 normalTowardsOrigin(Vec2 a, Vec2 b)
    {
        // So when will I get a cross product in R^2 haha
        double d = a.cross(b);
        Vec2 v = new Vec2(a.x.get() - b.x.get(), a.y.get() - b.y.get());
        if (d < Math.ulp(1.0d))
            return v.rotateLeft90();
        return v.rotateRight90();
    }

    private static boolean rayIntersectPortal(Vec2 o, Vec2 a, Vec2 b)
    {
        Vec2 nb = Vec2.negate(b);
        Vec2 na = Vec2.negate(a);

        double a1 = nb.cross(Vec2.subtract(o, b));
        double a2 = na.cross(Vec2.subtract(o, a));

        if(a1 != 0 && a2 != 0 && a1 * a2 < 0)
        {
            double a3 = a.cross(b);
            double a4 = a3 + a2 - a1;
            if(a3 != 0 && a4 != 0 && a3 * a4 < 0)
                return true;
        }

        return false;
    }

    public static boolean collide(IShape A, IShape B)
    {
        int maxIter = 0;

        // See algorithm as outlined in http://xenocollide.snethen.com/mpr2d.html

        // Phase 1: Portal Discovery
        // Figure 1b
        Vec2 v0  = Vec2.subtract(B.getCentroidWorld(), A.getCentroidWorld());
        v0.x.set(v0.x.get() + 0.00001);

        // Figure 1c
        Vec2 n   = Vec2.negate(v0);
        Vec2 v1  = Geometry.getMinkowskiDifference(A, B, n); // Basically Geometry.getMinkowskiDifference
        if(v1.dot(n) <= 0) return false; // Origin out of range of support, terminate early

        // Figure 1d
        n = normalTowardsOrigin(v1, v0);
        Vec2 v2 = Geometry.getMinkowskiDifference(A, B, n);
        if(v2.dot(n) <= 0) return false;

        // Phase 2: Portal Refinement
        while(true)
        {
            n = normalTowardsOrigin(v2, v1);
            if(!rayIntersectPortal(v0, v2, v1))
                n.negate();

            Vec2 v3 = Geometry.getMinkowskiDifference(A, B, n);
            if(v3.dot(n) <= 0) return false;

            if(Vec2.subtract(v3, v2).dot(n) <= 0.001 || maxIter++ > 20)
                return true;

            if(Geometry.originInTriangle(v0, v1, v3))
            {
                v2 = v3;
                continue;
            }
            else if(Geometry.originInTriangle(v0, v2, v3))
            {
                v1 = v3;
                continue;
            }

            return false;
        }
    }
}
