package com.chocksaway.bingo90.entities;

import java.util.List;

public class BingoStrip {
    private final int columnHeight;
    private int[][] overlay;

    public BingoStrip(final int columnHeight) {
        this.columnHeight = columnHeight;

        overlay = new int[columnHeight][9];
    }

    public void update(int[][] overlay) {

        final var bingoNumberGenerator = new BingoNumbersGenerator();

        for (int columnCounter = 0; columnCounter < 9; columnCounter++) {

            final var numbersToGetForColumn = countNonEmptyValuesInColumn(columnCounter);

            final var bingoNumbers = bingoNumberGenerator.getBingoNumbersForOneColumn(columnCounter, numbersToGetForColumn);

            writeValuesToColumn(overlay, columnCounter, bingoNumbers);
        }

        this.overlay = overlay;
    }


    private void writeValuesToColumn(int[][] overlay, int column, List<Integer> numbersForColumn) {
        final var iter = numbersForColumn.iterator();

        for (int columnCounter = 0; columnCounter < this.columnHeight; columnCounter++) {
            if (overlay[columnCounter][column] != -1 && iter.hasNext()) {
                overlay[columnCounter][column] = iter.next();
            }
        }
    }


    private int countNonEmptyValuesInColumn(int column) {
        if (column < 0 || column >= 9) {
            throw new IllegalArgumentException("Column index out of bounds: " + column);
        }

        int count = 0;

        for (int counter = 0; counter < this.columnHeight; counter++) {
            if (this.overlay[counter][column] != -1) {
                count++;
            }
        }

        return count;
    }

    public void print() {
        for (int[] row : this.overlay) {
            for (int num : row) {
                System.out.print("\t" + num + "\t");
            }
        }
    }

}
