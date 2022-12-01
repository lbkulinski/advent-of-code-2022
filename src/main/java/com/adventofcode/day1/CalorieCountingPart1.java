package com.adventofcode.day1;

import java.nio.file.Path;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.io.IOException;

public final class CalorieCountingPart1 {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day1/input.txt");

        int currentSum = 0;

        int maxSum = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                if (line.isEmpty()) {
                    if (currentSum > maxSum) {
                        maxSum = currentSum;
                    } //end if

                    currentSum = 0;

                    line = reader.readLine();

                    continue;
                } //end if

                int calorieAmount = Integer.parseInt(line);

                currentSum += calorieAmount;

                line = reader.readLine();
            } //end while
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        if (currentSum > maxSum) {
            maxSum = currentSum;
        } //end if

        System.out.printf("Max Sum: %d%n", maxSum);
    } //main
}