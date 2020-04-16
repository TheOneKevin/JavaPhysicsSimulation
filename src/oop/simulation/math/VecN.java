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

    /**
     * Constructs a new VecN object with specified dimension.
     *
     * @param dimension The dimension of the n-tuple.
     */
    public VecN(int dimension) {
        if (dimension > 0) {
            data = new double[dimension];
        } else {
            throw new IndexOutOfBoundsException("Dimensions must be greater than 0.");
        }
    }

    /**
     * Constructs a new VecN object with specified entries.
     *
     * @param inputArray The entries of the n-tuple.
     */
    public VecN(double... inputArray) {
        if (inputArray != null) {
            data = inputArray.clone();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Constructs a new VecN object from an existing one.
     *
     * @param other Another VecN object.
     */
    public VecN(VecN other) {
        data = other.data.clone();
    }

    /**
     * Returns the addition result between 2 n-tuples.
     *
     * @param u An VecN object.
     * @param v Another VecN object
     * @return The resultant n-tuple.
     */
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

    /**
     * Returns the Euclidean Inner Product of 2 n-tuples.
     *
     * @param u An VecN object.
     * @param v Another VecN object.
     * @return The value of the Euclidean Inner Product.
     */
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

    /**
     * Returns the Homogeneous Coordinates equivalent of an n-tuple.
     *
     * @param u An input VecN object.
     * @return The converted Homogeneous Coordinates VecN object.
     */
    public static VecN getHomoCoords(VecN u) {
        // given (x1, x2, ..., xn) return (x1, ..., xn, 1)
        double[] v = Arrays.copyOf(u.data, u.getDimension() + 1);
        v[v.length - 1] = 1;
        return new VecN(v);
    }

    /**
     * Returns the Cartesian n-tuple from Homogeneous Coordinates.
     *
     * @param u An input VecN object.
     * @return The converted Cartesian VecN object.
     */
    public static VecN wDivide(VecN u) {
        // given (x1, x2, ..., xn, w) return (x1/w, ..., xn/w)
        assert (u.getDimension() >= 2);
        double w = u.getEntryAt(u.getDimension());
        double[] v = new double[u.getDimension() - 1];
        for (int i = 0; i < v.length; i++)
            v[i] = u.data[i] / w;
        return new VecN(v);
    }

    /**
     * Returns the n-tuple multiplied by the Matrix T from the left.
     *
     * @param v An input VecN object.
     * @param T The Matrix transformation.
     * @return The resultant VecN object.
     */
    public static VecN matrixMultiply(VecN v, MatN T) {
        var u = v.clone();
        u.matrixMultiply(T);
        return u;
    }

    /**
     * Returns the inverse of the n-tuple.
     *
     * @param v An input VecN object.
     * @return The inverse of the VecN object.
     */
    public static VecN invert(VecN v) {
        VecN u = v.clone();
        u.invert();
        return u;
    }

    /**
     * Returns the dimension of the n-tuple.
     *
     * @param v An input VecN object.
     * @return The dimension of the VecN object.
     */
    public static int getDimension(VecN v) {
        return v.getDimension();
    }

    /**
     * Returns the entry value of a n-tuple at a specified position.
     *
     * @param v An input VecN object.
     * @param position The specified position.
     * @return The entry value.
     */
    public static double getEntryAt(VecN v, int position) {
        return v.getEntryAt(position);
    }

    /**
     * Returns a n-tuple with modified entry value at specified position.
     *
     * @param v An input VecN object.
     * @param c The value to be modified to.
     * @param position The specified position.
     * @return A VecN object with modified entry value.
     */
    public static VecN setEntryAt(VecN v, double c, int position) {
        VecN u = v.clone();
        u.setEntryAt(c, position);
        return u;
    }

    /**
     * Returns the Array that backs up the VecN object.
     *
     * @param v An VecN object.
     * @return An Array that backs up the VecN object.
     */
    public static double[] getArrayData(VecN v) {
        return v.getArrayData();
    }

    /**
     * Returns the n-tuple multiplied by a specified scalar.
     *
     * @param c The specified scalar.
     */
    public static VecN scalarMultiply(VecN v, double c) {
        var u = v.clone();
        u.scalarMultiply(c);
        return u;
    }

    /**
     * Adds a n-tuple to the current n-tuple.
     *
     * @param u The added n-tuple.
     */
    public void add(VecN u) {
        if (u.data.length != data.length) {
            throw new IndexOutOfBoundsException("Vector dimensions are not equal.");
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i] += u.data[i];
            }
        }
    }

    /**
     * Inverts the n-tuple. i.e. n=-n.
     */
    public void invert() {
        for (int i = 0; i < data.length; i++) {
            data[i] = 0 - data[i];
        }
    }

    /**
     * Multiplies the n-tuple by a specified scalar.
     *
     * @param c The specified scalar.
     */
    public void scalarMultiply(double c) {
        for (int i = 0; i < data.length; i++) {
            data[i] *= c;
        }
    }

    /**
     * Returns the dimension of the n-tuple.
     *
     * @return The dimension of the n-tuple.
     */
    public int getDimension() {
        return data.length;
    }

    /**
     * Returns the entry value at a specified location of the n-tuple.
     *
     * @param position The position of the entry.
     * @return The value of the entry.
     */
    public double getEntryAt(int position) {
        if (position > 0 && position < data.length + 1) {
            return data[position - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Sets the entry value at a specified location to the n-tuple.
     *
     * @param position The position of the entry.
     * @param v        The value to be assigned.
     */
    public void setEntryAt(double v, int position) {
        if (position > 0 && position < data.length + 1) {
            data[position - 1] = v;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Returns the Array that backs up the VecN object.
     *
     * @return An Array that backs up the VecN object.
     */
    public double[] getArrayData() {
        return data.clone();
    }

    /**
     * Multiplies the n-tuple by the specified Matrix from the left.
     *
     * @param T The specified matrix.
     */
    public void matrixMultiply(MatN T) {
        if (data.length == T.getNumberOfColumns()) {
            double[] v = new double[T.getNumberOfRows()];
            for (int i = 0; i < T.getNumberOfRows(); i++) {
                v[i] = getEuclideanInnerProduct(T.getRowData(i + 1), this);
            }
            data = v;
        } else {
            throw new ArithmeticException();
        }
    }

    /**
     * Returns the String representation of the n-tuple.
     *
     * @return The String representation of the VecN object.
     */
    public String toString() {
        String str = "(";
        for (int i = 0; i < data.length; i++) {
            str += Double.toString(data[i]);
            if (i != data.length - 1) {
                str += ", ";
            }
        }
        str += ")";
        return str;
    }

    /**
     * Sets the internal array to a different one.
     *
     * @param arr The new array.
     */
    public void set(double... arr) {
        data = arr.clone();
    }

    /**
     * Clones the VecN object.
     *
     * @return The clone VecN object.
     */
    public VecN clone() {
        return new VecN(data);
    }
}
