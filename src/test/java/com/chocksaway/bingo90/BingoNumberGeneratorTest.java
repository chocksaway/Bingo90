package com.chocksaway.bingo90;

import com.chocksaway.bingo90.entities.BingoNumbersGenerator;
import com.chocksaway.bingo90.entities.BingoStrip;
import com.chocksaway.bingo90.entities.BingoStripOverlay;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// rows, columns
public class BingoNumberGeneratorTest {
    static final int ROWS = 18;
    static final int COLUMNS = 9;

    static final int COLUMN_HEIGHT = 18;

    @Test
    public void testBingoNumberGeneratorReturn90Numbers() {
        final var bingoNumberGenerator = new BingoNumbersGenerator();

        final var bingoNumbers = bingoNumberGenerator.load();

        assertEquals(90, bingoNumbers.size());
    }

    @Test
    public void testGetEnoughNumbersForOneStrip() {
        final var bingoNumberGenerator = new BingoNumbersGenerator();
        final var bingoNumbers = bingoNumberGenerator.getBingoNumbersForOneStrip(1);

        assertEquals(15, bingoNumbers.size());
    }

    @Test
    public void testGetNumbersForOneColumn() {
        final var bingoNumberGenerator = new BingoNumbersGenerator();

        final var bingoNumbers = bingoNumberGenerator.getBingoNumbersForOneColumn(0, 3);

        assertEquals(3, bingoNumbers.size());

        assertEquals(87, bingoNumberGenerator.load().size());
    }

    @Test
    public void testMakeSureAll90BingoValuesAreUsed() {
        final var bingoNumberGenerator = new BingoNumbersGenerator();

        final var overlay = new BingoStripOverlay(ROWS,COLUMNS);

        final var layout = overlay.getLayout();

        final var bingoStrip = new BingoStrip(COLUMN_HEIGHT);

        bingoStrip.update(layout);

        // 4095 is the total of all the bingo numbers   1 + 2 + 3 + ... + 90
        assertEquals(bingoNumberGenerator.getTotal(), totalValuesInTicket(layout));
    }

    @Test
    public void printBingoStrip() {
        final var overlay = new BingoStripOverlay(ROWS,COLUMNS);

        final var layout = overlay.getLayout();

        final var bingoStrip = new BingoStrip(COLUMN_HEIGHT);

        bingoStrip.update(layout);

        bingoStrip.print(bingoStrip.update(layout));
    }

    /**
     * Calculate the total values in the bingo ticket
     * @param layout - the bingo ticket layout
     * @return total 4095
     */
    private int totalValuesInTicket(int[][] layout) {
        int totalBingoTicketNumbers = 0;

        for (int[] row : layout) {
            int runningTotal = Arrays.stream(row).filter(c -> c != -1).sum();

            totalBingoTicketNumbers += runningTotal;
        }
        return totalBingoTicketNumbers;
    }


    @Test
    public void testGetValuesForBingoTicketPerformance() {
        long startTime = System.nanoTime();
        final var overlay = new BingoStripOverlay(ROWS,COLUMNS);

        final var layout = overlay.getLayout();

        final var bingoTicket = new BingoStrip(COLUMN_HEIGHT);
        bingoTicket.update(layout);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Assert that the duration is within an acceptable range (e.g., less than 1 second)
        assertTrue(duration < 1_000_000_000, "Performance test failed: populateArray took too long");
    }

    @Test
    public void testGetValuesForBingoTicketPerformanceWithVirtualThreads() throws InterruptedException {
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        long startTime = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            executor.submit(() -> {
                var overlay = new BingoStripOverlay(18, 9);
                var layout = overlay.getLayout();
                var bingoTicket = new BingoStrip(COLUMN_HEIGHT);
                bingoTicket.update(layout);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Assert that the duration is within an acceptable range (e.g., less than 1 minute)
        assertTrue(duration < 60_000_000_000L, "Performance test failed: populateArray took too long");
    }
}
