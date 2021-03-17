package lab6.matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Matrix {
    private int size = 10;
    private double[][] _Matrix = new double[size][size];
    private boolean filled = false;
    private String path;

    public Matrix(String path) {
        this.path = path;
    }

    public double[][] get_Matrix() {
        return _Matrix;
    }

    private static double kahanSum(double... fa) {
        double sum = 0.0;
        double c = 0.0;
        for (double f : fa) {
            double y = f - c;
            double t = sum + y;
            c = (t - sum) - y;
            sum = t;
        }
        return sum;
    }

    public Matrix filledWithRandomValues() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    double value = 1000.0 * new Random().nextDouble();
                    _Matrix[i][j] = value;
                    bw.write(String.valueOf(value) + ", ");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            filled = false;
        }
        filled = true;
        return this;
    }

    public Matrix multiplyWithMatrix(Matrix matrix, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        double[][] currentMatrix = matrix.get_Matrix();

        for (int i = from; i < to - 1; i++) {
            for (int k = 0; k < size; k++) {
                double a = 0;
                for (int j = 0; j < size; j++) {
                    a = kahanSum(a, _Matrix[i][j] * currentMatrix[j][k]);

                }
                _Matrix[i][k] = a;
            }
        }
        return this;
    }

    public Matrix multiplyWithDouble(double d, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        for (int i = from; i < to; i++) {
            for (int k = 0; k < size; k++) {
                _Matrix[i][k] = _Matrix[i][k] * d;
            }
        }
        return this;
    }

    public Matrix sumWithMatrix(Matrix matrix, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");
        double[][] currentMatrix = matrix.get_Matrix();
        for (int i = from; i < to; i++) {
            for (int j = 0; j < size; j++) {
                _Matrix[i][j] = _Matrix[i][j] + currentMatrix[i][j];
            }
        }
        return this;
    }

    public Matrix printToConsole() {
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(_Matrix[i]));
        }
        return this;
    }

    public Matrix saveToFile(String newpath) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newpath));
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    bw.write(String.valueOf(_Matrix[i][j]) + ", ");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
        return this;
    }
}
