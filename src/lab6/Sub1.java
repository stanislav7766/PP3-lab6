package lab6;

import java.util.concurrent.*;
import lab6.vector.*;
import lab6.matrix.*;

public class Sub1 {
    public synchronized void run() throws InterruptedException {
        // Варіант 18, 1) MА= min(D+ В)*MD*MT+MX*ME;

        System.out.println("Starting sub1...");
        long startTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        Vector D = new Vector("./sub1/D.txt").filledWithRandomValues();
        Vector B = new Vector("./sub1/B.txt").filledWithRandomValues();

        Matrix MD = new Matrix("./sub1/MD.txt").filledWithRandomValues();
        Matrix MT = new Matrix("./sub1/MT.txt").filledWithRandomValues();
        Matrix MX = new Matrix("./sub1/MX.txt").filledWithRandomValues();
        Matrix ME = new Matrix("./sub1/ME.txt").filledWithRandomValues();

        double[] min_D_B = new double[1];
        int defaultSize = 10;
        // D+B
        pool.invoke(new VectorParallel("sum-vector", 0, defaultSize, D, B, null, null));
        // min(D+B)
        pool.invoke(new VectorParallel("find-min", 0, defaultSize, D, null, null, min_D_B));
        // MX*ME
        pool.invoke(new MatrixParallel("mul-matrix", 0, defaultSize, MX, ME, null));
        // MD*MT
        pool.invoke(new MatrixParallel("mul-matrix", 0, defaultSize, MD, MT, null));
        // min(D+B)*(MD*MT)
        pool.invoke(new MatrixParallel("mul-double", 0, defaultSize, MD, null, min_D_B));
        // min(D+B)*(MD*MT)+ (MX*ME)
        pool.invoke(new MatrixParallel("sum-matrix", 0, defaultSize, MX, MD, null));
        System.out.println("MA:");
        MX.printToConsole().saveToFile("./sub1/MA.txt");

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time sub1 in millis: " + elapsedTime / 1000000);

    }

}
