package lab6.vector;

import java.util.concurrent.RecursiveAction;
import lab6.matrix.Matrix;

public class VectorParallel extends RecursiveAction {

    private static final long serialVersionUID = 1L;
    String operation;
    int from;
    int to;
    Vector vector;
    Vector vector1;
    Matrix matrix;
    double[] min;

    public VectorParallel(String operation, int from, int to, Vector vector, Vector vector1, Matrix matrix,
            double[] min) {
        this.operation = operation;
        this.to = to;
        this.from = from;
        this.vector = vector;
        this.vector1 = vector1;
        this.matrix = matrix;
        this.min = min;
    }

    @Override
    protected void compute() {
        if (operation == "find-min") {
            vector.findMin(min);
            return;
        }
        if ((to - from) < 2) {
            switch (operation) {
            case "mul-matrix":
                vector.multiplyWithMatrix(matrix, from, to);
                break;
            case "mul-double":
                vector.multiplyWithDouble(min[0], from, to);
                break;
            case "sum-vector":
                vector.sumWithVector(vector1, from, to);
                break;
            default:
                break;
            }
        } else {
            int mid = (from + to) / 2;
            invokeAll(new VectorParallel(operation, from, mid, vector, vector1, matrix, min),
                    new VectorParallel(operation, mid, to, vector, vector1, matrix, min));
        }
    }
}
