package com.adventofcode.day1;

import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.io.IOException;
import java.util.TreeSet;

public final class CalorieCountingPart2 {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day1/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        int currentSum = 0;

        TreeSet<Integer> calorieSums = new TreeSet<>();

        for (String line : lines) {
            if (line.isEmpty()) {
                calorieSums.add(currentSum);

                currentSum = 0;

                continue;
            } //end if

            int calorieAmount = Integer.parseInt(line);

            currentSum += calorieAmount;
        } //end for

        calorieSums.add(currentSum);

        int topThreeSum = calorieSums.descendingSet()
                                     .stream()
                                     .limit(3)
                                     .reduce(Integer::sum)
                                     .orElseThrow();

        System.out.printf("Top Three Sum: %d%n", topThreeSum);
    } //main
}