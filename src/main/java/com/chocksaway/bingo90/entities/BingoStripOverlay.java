package com.chocksaway.bingo90.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BingoStripOverlay {
    private final int[][] layout;

    public BingoStripOverlay(int rows, int columns) {
        layout = new int[rows][columns];
        populateArray(layout, rows, columns);
    }

    public int[][] getLayout() {
        return this.layout;
    }

    private void populateArray(int[][] array, int rows, int columns) {
        Random random = new Random();

        // Place -1 values randomly in each row ensuring exactly 4 per row
        for (int row = 0; row < rows; row++) {
            Set<Integer> placedColumns = new HashSet<>();
            while (placedColumns.size() < 4) {
                placedColumns.add(random.nextInt(columns));
            }
            for (int col : placedColumns) {
                array[row][col] = -1;
            }
        }

        // Adjust the number of zeros in each column
        adjustZeros(array, rows, columns);
    }

    private void adjustZeros(int[][] array, int rows, int columns) {
        int[] zeroCounts = new int[columns];

        // Count initial zeros in each column
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                if (array[row][col] == 0) {
                    zeroCounts[col]++;
                }
            }
        }

        // Adjust each column's zero count to match the desired targets
        adjustColumn(array, zeroCounts, 0, 9);     // First column: 9 zeros
        adjustColumn(array, zeroCounts, columns - 1, 11);  // Last column: 11 zeros

        // Adjust the other columns to have 10 zeros
        for (int col = 1; col < columns - 1; col++) {
            adjustColumn(array, zeroCounts, col, 10);  // Other columns: 10 zeros
        }
    }

    private void adjustColumn(int[][] array, int[] zeroCounts, int col, int targetZeros) {
        Random random = new Random();

        // Adjust zeros down if we have too many
        while (zeroCounts[col] > targetZeros) {
            int row = random.nextInt(array.length);
            if (array[row][col] == 0) {
                array[row][col] = -1;
                zeroCounts[col]--;
            }
        }

        // Adjust zeros up if we have too few
        while (zeroCounts[col] < targetZeros) {
            int row = random.nextInt(array.length);
            if (array[row][col] == -1) {
                array[row][col] = 0;
                zeroCounts[col]++;
            }
        }
    }
}