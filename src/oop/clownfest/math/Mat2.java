package oop.clownfest.math;

/**
 * Matrix representations of linear transformations T.
 * T: R^2 -> R^2 where T is linear.
 *
 * @author Kai Qi Hao (nova-terra)
 * @version April 14th, 2020
 */
public class Mat2
{
    private double[][] mat;

    public Mat2(double a, double b, double c, double d)
    {
        mat = new double[2][2];
        mat[0][0]=a;
        mat[0][1]=b;
        mat[1][0]=c;
        mat[1][1]=d;
    }

    public double getValueAt(int p_x,int p_y)
    {
        try
        {
            return mat[p_x][p_y];
        }
        catch(java.lang.Exception e)
        {
            return 0;
        }
    }

    public double getDet()
    {
        return mat[0][0]*mat[1][1]-mat[0][1]*mat[1][0];
    }
}
