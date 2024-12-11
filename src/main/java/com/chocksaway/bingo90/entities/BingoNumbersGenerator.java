package com.chocksaway.bingo90.entities;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;


public class BingoNumbersGenerator implements BingoData {
    final Queue<ImmutablePair<Integer, Integer>> numbersQueue;

    public BingoNumbersGenerator() {
        numbersQueue = new LinkedList<>(BingoData.bingoNumbers);
        Collections.shuffle((List<?>) numbersQueue);
    }

    public List<ImmutablePair<Integer, Integer>> getBingoNumbers() {
        return List.copyOf(numbersQueue);
    }

    @Override
    public int getTotal() {
        return numbersQueue.stream().map(ImmutablePair::getLeft).reduce(0, Integer::sum);
    }

    public List<ImmutablePair<Integer,Integer>> getBingoNumbersForOneStrip(final int numberOfStrip) {
        return List.copyOf(numbersQueue).subList(0, numberOfStrip * 15);
    }

    public List<Integer> getBingoNumbersForOneColumn(final int column, final int number) {
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