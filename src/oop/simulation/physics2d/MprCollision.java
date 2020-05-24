package oop.simulation.physics2d;

import oop.simulation.math.Geometry;
import oop.simulation.math.Vec2;

/**
 * This is in charge of detecting where the game object will go after it's collision.
 *
 * @author Kevin Dai
 */

public class MprCollision
{
    /**
     * This finds the way to the origin of the game object.
     * @param a         This is the coordinate of a point on the outside of the game object
     * @param b         this is the coordinate of a point on the outside of the game object
     * @return
     */
    private static Vec2 normalTowardsOrigin(Vec2 a, Vec2 b)
    {
        // So when will I get a cross product in R^2 haha
        double d = a.cross(b);
        Vec2 v = new Vec2(a.x.get() - b.x.get(), a.y.get() - b.y.get());
        if (d < Math.ulp(1.0d))
            return v.rotateLeft90();
        return v.rotateRight90();
    }

    /**
     * This navigates the ray intersection portal
     * @param o         A coordinate of intersection
     * @param a         A coordinate of intersection
     * @param b         A coordinate of intersection
     */
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

    /**
     * Determines whether or not there was a collision
     * @param A         The shape of the first game object
     * @param B         The shape of the second game object
     * @param m         The math involved in a collision
     */
    public static boolean collide(IShape A, IShape B, Manifold m)
    {
        int maxIter = 0;

        // See algorithm as outlined in http://xenocollide.snethen.com/mpr2d.html

        // Phase 1: Portal Discovery
        // Figure 1b
        Vec2 v0  = Vec2.subtract(B.getCentroidWorld(), A.getCentroidWorld());
        v0.x.set(v0.x.get() + 0.00001);

        // Figure 1c
        Vec2 n   = Vec2.negate(v0);
        Vec2 v1  = Geometry.getMinkowskiDifference(A, B, n);
        Vec2 v1n = n.clone();
        if(v1.dot(n) <= 0) return false; // Origin out of range of support, terminate early

        // Figure 1d
        n = normalTowardsOrigin(v1, v0);
        Vec2 v2 = Geometry.getMinkowskiDifference(A, B, n);
        Vec2 v2n = n.clone();
        if(v2.dot(n) <= 0) return false;

        // Phase 2: Portal Refinement
        while(true)
        {
            // Figure 1g, get normal, find support
            n = normalTowardsOrigin(v2, v1);
            if(!rayIntersectPortal(v0, v2, v1))
                n.negate();
            Vec2 v3 = Geometry.getMinkowskiDifference(A, B, n);
            if(v3.dot(n) <= 0) return false;

            // Terminate.
            if(Vec2.subtract(v3, v2).dot(n) <= 0.0001 || maxIter++ > 15)
            {
                // ** This routine has been ported over from the Blaze MPR code. ** //
                // Which side of the line does the origin lie on? (test -v1 dot (v2-v1))
                Vec2 ba = Vec2.subtract(v2, v1);
                double t = - v1.dot(ba);
                Vec2 p;
                if(t <= 0)
                {
                    t = 0;
                    p = v1;
                }
                else
                {
                    double len = Vec2.getEuclideanInnerProduct(ba, ba);
                    if(t >= len)
                    {
                        t = 1;
                        p = v2;
                    }
                    else
                    {
                        t /= len;
                        p = Vec2.add(v1, Vec2.scalarMultiply(ba, t));
                    }
                }

                Vec2 v11 = A.getSupportWorld(Vec2.negate(v1n));
                Vec2 v12 = B.getSupportWorld(v1n);
                Vec2 v21 = A.getSupportWorld(Vec2.negate(v2n));
                Vec2 v22 = B.getSupportWorld(v2n);

                m.contacts[0] = Vec2.scalarMultiply(v11, 1 - t).add(Vec2.scalarMultiply(v21, t));
                m.contacts[1] = Vec2.scalarMultiply(v12, 1 - t).add(Vec2.scalarMultiply(v22, t));
                m.penetration = Math.sqrt(p.lengthSq());
                p.normalize();
                m.normal = p;

                return true;
            }

            // Figure 1h and 1i, discard unused point.
            if(Geometry.originInTriangle(v0, v1, v3))
            {
                v2 = v3;
                v2n = n.clone();
                continue;
            }
            else if(Geometry.originInTriangle(v0, v2, v3))
            {
                v1 = v3;
                v1n = n.clone();
                continue;
            }

            return false;
        }
    }
}
