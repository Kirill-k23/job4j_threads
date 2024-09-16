package ru.job4j.concurrent.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        CompletableFuture[] c = new CompletableFuture[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums();
            int rowIndex = i;
            c[i] = CompletableFuture.runAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < n; j++) {
                    rowSum += matrix[rowIndex][j];
                    colSum += matrix[j][rowIndex];
                }
                sums[rowIndex].setRowSum(rowSum);
                sums[rowIndex].setColSum(colSum);
            });
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(c);
        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return sums;
    }
}
