package com.adventofcode.day1;

import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.io.IOException;

public final class CalorieCountingPart1 {
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

        int maxSum = 0;

        for (String line : lines) {
            if (line.isEmpty()) {
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                } //end if

                currentSum = 0;

                continue;
            } //end if

            int calorieAmount = Integer.parseInt(line);

            currentSum += calorieAmount;
        } //end for

        if (currentSum > maxSum) {
            maxSum = currentSum;
        } //end if

        System.out.printf("Max Sum: %d%n", maxSum);
    } //main
}