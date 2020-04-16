package oop.simulation.math;

/**
 * Matrix representations of T.
 * T: R^n -> R^m.
 * <p>
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
        data = other.data.clone();
    }

    /**
     * Returns the matrix multiplication with specified matrix from the right.
     *
     * @param T The specified matrix.
     */
    public static MatN matrixMultiply(MatN T, MatN U) {
        MatN L = T.clone();
        L.matrixMultiply(U);
        return L;
    }

    public static MatN identityN(int n) {
        if (n > 0) {
            double[][] data = new double[n][n];
            for (int i = 0; i < n; i++)
                data[i][i] = 1;
            return new MatN(data);
        } else
            throw new IndexOutOfBoundsException();
    }

    public static VecN getRowData(MatN T, int row) {
        return T.getRowData(row);
    }

    public static VecN getColumnData(MatN T, int column) {
        return T.getColumnData(column);
    }

    public static MatN setRowData(MatN T, VecN v, int row) {
        MatN U = T.clone();
        U.setRowData(v, row);
        return U;
    }

    public static MatN setColumnData(MatN T, VecN v, int column) {
        MatN U = T.clone();
        U.setColumnData(v, column);
        return U;
    }

    public static MatN setEntryAt(MatN T, double c, int row, int column) {
        MatN U = T.clone();
        U.setEntryAt(c, row, column);
        return U;
    }

    public static MatN transpose(MatN T) {
        MatN U = T.clone();
        U.transpose();
        return U;
    }

    public static double getNumberOfRows(MatN T) {
        return T.getNumberOfRows();
    }

    public static double getNumberOfColumns(MatN T) {
        return T.getNumberOfColumns();
    }

    public static double[][] getArrayData(MatN T) {
        return T.getArrayData();
    }

    public static MatN scalarMultiply(MatN T, double c){
        MatN U = T.clone();
        U.scalarMultiply(c);
        return U;
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

    public void setRowData(VecN v, int row) {
        if (row > 0 && row < data.length + 1 && v.getDimension() == getNumberOfColumns()) {
            data[row - 1] = v.getArrayData();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setColumnData(VecN v, int column) {
        if (column > 0 && column < data[0].length + 1 && v.getDimension() == getNumberOfRows()) {
            for (int i = 0; i < data.length; i++)
                data[i][column - 1] = v.getEntryAt(i + 1);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setEntryAt(double c, int row, int col) {
        if (col > 0 && col < data[0].length + 1 && row > 0 && row < data.length + 1)
            data[row][col] = c;
        else
            throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the matrix multiplication with specified matrix from the right.
     *
     * @param T The specified matrix.
     */
    public void matrixMultiply(MatN T) {
        assert (getNumberOfColumns() == T.getNumberOfRows());
        {
            double[][] A = new double[getNumberOfRows()][T.getNumberOfColumns()];
            for (int i = 0; i < getNumberOfRows(); i++) {
                for (int j = 0; j < T.getNumberOfColumns(); j++) {
                    A[i][j] = VecN.getEuclideanInnerProduct(getRowData(i + 1), T.getColumnData(j + 1));
                }
            }
            data = A;
        }
    }

    public void scalarMultiply(double c){
        double[][] A = new double[getNumberOfRows()][getNumberOfColumns()];
        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfColumns(); j++) {
                A[i][j] = c*data[i][j];
            }
        }
        data = A;
    }

    public void transpose() {
        double[][] A = new double[getNumberOfColumns()][getNumberOfRows()];
        for (int i = 0; i < getNumberOfColumns(); i++) {
            for (int j = 0; j < getNumberOfRows(); j++) {
                A[i][j] = data[j][i];
            }
        }
        data = A;
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

    public MatN clone() {
        return new MatN(data);
    }

    public String toString() {
        String str = "{\n";
        for (int i = 0; i < getNumberOfRows(); i++) {
            str += getRowData(i + 1).toString();
            if (i != getNumberOfRows() - 1) {
                str += ",\n";
            }
        }
        str += "\n}";
        return str;
    }
}
