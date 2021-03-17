package lab6.vector;

import lab6.matrix.Matrix;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Vector {
    private int size = 10;
    private double[] _Vector = new double[size];
    private boolean filled = false;
    private String path;

    public Vector(String path) {
        this.path = path;
    }

    public double[] get_Vector() {
        return _Vector;
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

    public Vector filledWithRandomValues() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < size; i++) {
                double value = 1000.0 * new Random().nextDouble();
                _Vector[i] = value;
                bw.write(String.valueOf(value) + ", ");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            filled = false;
        }
        filled = true;
        return this;
    }

    public Vector saveToFile(String newpath) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newpath));
            for (int i = 0; i < size; i++) {
                bw.write(String.valueOf(_Vector[i]) + ", ");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
        return this;
    }

    public double findMin(double[] output) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        double min = Arrays.stream(_Vector).reduce(_Vector[0], (x, y) -> x > y ? y : x);
        output[0] = min;
        return min;
    }

    public Vector multiplyWithMatrix(Matrix matrix, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        double[][] currentMatrix = matrix.get_Matrix();
        for (int i = from; i < to - 1; i++) {
            double a = 0.0;
            for (int j = 0; j < size; j++) {
                a = kahanSum(a, _Vector[j] * currentMatrix[j][i]);
            }
            _Vector[i] = a;
        }
        return this;
    }

    public Vector multiplyWithDouble(double d, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        for (int i = from; i < to; i++) {
            _Vector[i] = _Vector[i] * d;
        }
        return this;
    }

    public Vector sumWithVector(Vector vector, int from, int to) {
        if (!filled)
            throw new Error("Спочатку необхідно викликати filledWithRandomValues");

        double[] currentVector = vector.get_Vector();
        for (int i = from; i < to; i++) {
            _Vector[i] = _Vector[i] + currentVector[i];
        }
        return this;
    }

    public Vector printToConsole() {
        System.out.println(Arrays.toString(_Vector));
        return this;
    }
}
