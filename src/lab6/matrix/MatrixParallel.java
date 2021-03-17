package lab6.matrix;

import java.util.concurrent.RecursiveAction;

public class MatrixParallel extends RecursiveAction {

    private static final long serialVersionUID = 1L;
    String operation;
    int from;
    int to;
    Matrix matrix1;
    Matrix matrix2;
    double[] min;

    public MatrixParallel(String operation, int from, int to, Matrix matrix1, Matrix matrix2, double[] min) {
        this.operation = operation;
        this.to = to;
        this.from = from;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.min = min;
    }

    @Override
    protected void compute() {
        if ((to - from) < 2) {
            switch (operation) {
            case "sum-matrix":
                matrix1.sumWithMatrix(matrix2, from, to);
                break;
            case "mul-matrix":
                matrix1.multiplyWithMatrix(matrix2, from, to);
                break;
            case "mul-double":
                matrix1.multiplyWithDouble(min[0], from, to);
                break;
            default:
                break;
            }
        } else {
            int mid = (from + to) / 2;
            invokeAll(new MatrixParallel(operation, from, mid, matrix1, matrix2, min),
                    new MatrixParallel(operation, mid, to, matrix1, matrix2, min));
        }
    }
}
