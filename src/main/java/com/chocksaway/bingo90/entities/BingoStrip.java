package com.chocksaway.bingo90.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BingoStrip {
    private static final Logger logger = LoggerFactory.getLogger(BingoStrip.class);
    private final int columnHeight;

    private int[][] overlay;

    public BingoStrip(final int columnHeight) {
        this.columnHeight = columnHeight;
        overlay = new int[columnHeight][9];
    }

    public void update(int[][] overlay) {
        logger.debug("Updating overlay with bingo numbers");

        final var bingoNumberGenerator = new BingoNumbersGenerator();

        for (int columnCounter = 0; columnCounter < 9; columnCounter++) {

            final var numbersToGetForColumn = countNonEmptyValuesInColumn(columnCounter, overlay);

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


    private int countNonEmptyValuesInColumn(int column, int[][] overlay) {
        if (column < 0 || column >= 9) {
            throw new IllegalArgumentException("Column index out of bounds: " + column);
        }

        int count = 0;

        for (int counter = 0; counter < this.columnHeight; counter++) {
            if (overlay[counter][column] != -1) {
                count++;
            }
        }

        return count;
    }

    @Override
    public java.lang.String toString() {
        return print();
    }

    private String print() {
        final var sb = new StringBuilder();
        var marker = 0;
        sb.append("\n-----------------------------------------------------------------------\n");

        for (int[] row : overlay) {
            for (int num : row) {
                final String numStr = num == -1 ? "X" : String.valueOf(num);
                sb.append("\t").append(numStr).append("\t");
            }

            if (marker == 2) {
                sb.append("\n-----------------------------------------------------------------------\n");
                marker = 0;
            } else {
                sb.append("\n");
                marker++;
            }
        }

        return sb.toString();
    }

}
