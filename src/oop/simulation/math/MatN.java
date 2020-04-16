package oop.simulation.math;

/**
 * Matrix representations of T.
 * T: R^n -> R^m.
 * <p>
 * (Yes I'm well aware that using VecN to implement MatN would be a better idea but I'm too lazy to change my code)
 * <p>
 * also I will add documentations later this week
 *
 * @author Kai Qi Hao (nova-terra)
 */
public class MatN {
    protected double[][] data;

    public MatN(int m, int n) {
        if (m > 0 && n > 0) {
            data = new double[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    data[i][j] = 0;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public MatN(double[][] input) {
        if (input != null) {
            data = new double[input.length][input[0].length];
            for (int i = 0; i < input.length; i++) {
                for (int j = 0; j < input[0].length; j++) {
                    data[i][j] = input[i][j];
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    public MatN(MatN other) {
        data = other.data;
    }

    public VecN getRowData(int row) {
        if (row > 0 && row < data.length + 1) {
            return new VecN(data[row - 1]);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public VecN getColumnData(int column) {
        if (column > 0 && column < data[0].length + 1) {
            double[] v = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                v[i] = data[i][column - 1];
            }
            return new VecN(v);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int getNumberOfRows() {
        return data.length;
    }

    public int getNumberOfColumns() {
        return data[0].length;
    }

    public double[][] getArrayData() {
        return data;
    }
}
