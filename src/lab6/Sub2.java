package lab6;

import java.util.concurrent.*;
import lab6.vector.*;
import lab6.matrix.*;

public class Sub2 {

    public synchronized void run() throws InterruptedException {
        // Варіант 18, 2) C=В*МT+D*MX*a;

        System.out.println("Starting sub2...");
        long startTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        Vector B = new Vector("./sub2/B.txt").filledWithRandomValues();
        Vector D = new Vector("./sub2/D.txt").filledWithRandomValues();

        Matrix MT = new Matrix("./sub2/MT.txt").filledWithRandomValues();
        Matrix MX = new Matrix("./sub2/MX.txt").filledWithRandomValues();

        double[] fixed_a = { 2 };
        int defaultSize = 10;

        // B*MT
        pool.invoke(new VectorParallel("mul-matrix", 0, defaultSize, B, null, MT, null));
        // a*D
        pool.invoke(new VectorParallel("mul-double", 0, defaultSize, D, null, null, fixed_a));
        // (a*D)*MX
        pool.invoke(new VectorParallel("mul-matrix", 0, defaultSize, D, null, MX, null));
        // ((a*D)*MX) + (B*MT)
        pool.invoke(new VectorParallel("sum-vector", 0, defaultSize, B, D, null, null));

        System.out.println("C:");
        B.printToConsole().saveToFile("./sub2/C.txt");

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time sub2 in millis: " + elapsedTime / 1000000);

    }

}
