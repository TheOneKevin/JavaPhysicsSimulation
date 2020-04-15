package oop.clownfest.math;

/**
 * 2-tuples in R^2 and their operations.
 *
 * @author Kai Qi Hao (nova-terra)
 * @version April 14th, 2020
 */
public class Vec2
{
    private double x, y;

    public Vec2(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void add(Vec2 u)
    {
        this.x+=u.x;
        this.y=u.y;
    }

    public static Vec2 add(Vec2 u, Vec2 v)
    {
        return new Vec2(u.x+v.x,u.y+v.y);
    }

    public void invert()
    {
        this.x=-this.x;
        this.y=-this.y;
    }

    public static double getEucInnerProduct(Vec2 u, Vec2 v)
    {
        return u.x*v.x+u.y*v.y;
    }

    public void LT(Mat2 A)
    {
        this.x=this.x*A.getValueAt(0,0)+this.y*A.getValueAt(0,1);
        this.y=this.x*A.getValueAt(1,0)+this.y*A.getValueAt(1,1);
    }
}
