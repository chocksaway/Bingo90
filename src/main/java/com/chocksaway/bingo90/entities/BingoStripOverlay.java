package com.chocksaway.bingo90.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BingoStripOverlay {
    private static final Logger logger = LoggerFactory.getLogger(BingoStripOverlay.class);
    private final int[][] layout;

    public BingoStripOverlay(int rows, int columns) {
        this.layout = new int[rows][columns];
        populate();
    }

    public int[][] getLayout() {
        return this.layout;
    }

    private void populate() {
        logger.debug("Populate the layout");
        for (int row = 0; row < 18; row++) {
            for (int col = 0; col < 9; col++) {
                this.layout[row][col] = 0;
            }
        }

        for (int outer = 0; outer < 18; outer++) {
            if (outer % 2 == 0) {
                this.layout[outer][0] = -1;
                this.layout[outer][2] = -1;
                this.layout[outer][4] = -1;
                this.layout[outer][6] = -1;
            } else {
                this.layout[outer][1] = -1;
                this.layout[outer][3] = -1;
                this.layout[outer][5] = -1;
                this.layout[outer][7] = -1;
            }

            if (this.layout[outer][7] == -1) {
                this.layout[outer][8] = 0;
            } else {
                this.layout[outer][8] = -1;
            }
        }

        this.layout[1][1] = 0;
        this.layout[2][2] = 0;
        this.layout[3][3] = 0;
        this.layout[4][4] = 0;
        this.layout[5][5] = 0;
        this.layout[6][6] = 0;
        this.layout[7][7] = 0;

        this.layout[4][8] = 0;
        this.layout[8][8] = 0;
    }
}