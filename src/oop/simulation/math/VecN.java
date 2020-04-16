package oop.simulation.math;

import java.util.Arrays;

/**
 * n-tuples in R^n and their operations.
 * <p>
 * <p>
 * will add documentations this week
 *
 * @author Kai Qi Hao (nova-terra)
 */
public class VecN {
    protected double[] data;

    public VecN(int dimension) {
        if (dimension > 0) {
            data = new double[dimension];
        } else {
            throw new IndexOutOfBoundsException("Dimensions must be greater than 0.");
        }
    }

    public VecN(double ... inputArray) {
        if (inputArray != null) {
            data = inputArray.clone();
        } else {
            throw new NullPointerException();
        }
    }

    public VecN(VecN other) {
        data = other.data.clone();
    }

    public static VecN add(VecN u, VecN v) {
        if (u.data.length != v.data.length) {
            throw new IndexOutOfBoundsException("Vector dimensions are not equal.");
        } else {
            double[] w = new double[u.data.length];
            for (int i = 0; i < u.data.length; i++) {
                w[i] = u.data[i] + v.data[i];
            }
            return new VecN(w);
        }
    }

    public static double getEuclideanInnerProduct(VecN u, VecN v) {
        if (u.data.length != v.data.length) {
            throw new IndexOutOfBoundsException("Vector dimensions are not equal.");
        } else {
            double result = 0;
            for (int i = 0; i < u.data.length; i++) {
                result += u.data[i] * v.data[i];
            }
            return result;
        }
    }

    public static VecN getHomoCoords(VecN u)
    {
        // given (x1, x2, ..., xn) return (x1, ..., xn, 1)
        double[] v = Arrays.copyOf(u.data, u.getDimension() + 1);
        v[v.length-1] = 1;
        return new VecN(v);
    }

    public static VecN getCartFromHomo(VecN u)
    {
        // given (x1, x2, ..., xn, w) return (x1/w, ..., xn/w)
        assert(u.getDimension() >= 2);
        double w = u.getEntryAt(u.getDimension());
        double[] v = new double[u.getDimension() - 1];
        for(int i = 0; i < v.length; i++)
            v[i] = u.data[i] / w;
        return new VecN(v);
    }

    public static VecN matrixTransform(VecN v, MatN T)
    {
        var u = v.clone();
        u.matrixTransform(T);
        return u;
    }

    public void add(VecN u) {
        if (u.data.length != data.length) {
            throw new IndexOutOfBoundsException("Vector dimensions are not equal.");
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i] += u.data[i];
            }
        }
    }

    public void invert() {
        for (int i = 0; i < data.length; i++) {
            data[i] = 0 - data[i];
        }
    }

    public int getDimension() {
        return data.length;
    }

    public double getEntryAt(int position) {
        if (position > 0 && position < data.length + 1) {
            return data[position - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setEntryAt(int position, double v) {
        if (position > 0 && position < data.length + 1) {
            data[position - 1] = v;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double[] getArrayData() {
        return data.clone();
    }

    public void matrixTransform(MatN T) {
        if (data.length == T.getNumberOfColumns()) {
            double[] v = new double[T.getNumberOfRows()];
            for (int i = 0; i < T.getNumberOfRows(); i++) {
                v[i] = getEuclideanInnerProduct(T.getRowData(i + 1), this);
            }
            this.data = v;
        } else {
            throw new ArithmeticException();
        }
    }

    public String toString() {
        String output = "";
        output += "(";
        for (int i = 0; i < data.length; i++) {
            output += Double.toString(data[i]);
            if (i != data.length - 1) {
                output += ", ";
            }
        }
        output += ")";
        return output;
    }

    public void set(double ... arr) {
        data = arr.clone();
    }

    public VecN clone()
    {
        return new VecN(data);
    }
}
