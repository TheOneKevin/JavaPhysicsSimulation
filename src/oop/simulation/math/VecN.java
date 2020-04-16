package oop.simulation.math;

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

    public static double getEntryAt(VecN u, int position) {
        return u.getEntryAt(position);
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
}
