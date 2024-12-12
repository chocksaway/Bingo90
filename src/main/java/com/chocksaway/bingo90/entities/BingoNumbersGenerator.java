package com.chocksaway.bingo90.entities;

import org.apache.commons.lang3.tuple.ImmutablePair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class BingoNumbersGenerator implements BingoData {
    private static final Logger logger = LoggerFactory.getLogger(BingoNumbersGenerator.class);
    final Queue<ImmutablePair<Integer, Integer>> numbersQueue;

    public BingoNumbersGenerator() {
        numbersQueue = new LinkedList<>(BingoData.bingoNumbers);
        Collections.shuffle((List<?>) numbersQueue);
        logger.debug("Bingo numbers shuffled and loaded into the queue.");
    }

    public List<ImmutablePair<Integer, Integer>> load() {
        logger.debug("Loading bingo numbers.");
        return List.copyOf(numbersQueue);
    }

    @Override
    public int getTotal() {
        logger.debug("Total of all bingo numbers");
        return numbersQueue.stream().map(ImmutablePair::getLeft).reduce(0, Integer::sum);
    }

    public List<ImmutablePair<Integer,Integer>> getBingoNumbersForOneStrip(final int numberOfStrip) {
        return List.copyOf(numbersQueue).subList(0, numberOfStrip * 15);
    }

    public List<Integer> getBingoNumbersForOneColumn(final int column, final int number) {
        logger.debug("Getting {} bingo numbers for column: {}", number, column);
        List<Integer> numbers = new LinkedList<>();
        Iterator<ImmutablePair<Integer, Integer>> iterator = numbersQueue.iterator();

        while (numbers.size() < number && iterator.hasNext()) {
            ImmutablePair<Integer, Integer> pair = iterator.next();
            if (pair.getRight() == column) {
                numbers.add(pair.getLeft());
                iterator.remove();
            }
        }

        Collections.sort(numbers);

        return numbers;
    }
}