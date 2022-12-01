package com.adventofcode.day1;

import java.nio.file.Path;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.io.IOException;

public final class CalorieCountingPart2 {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day1/input.txt");

        int currentSum = 0;

        int firstMaxSum = 0;

        int secondMaxSum = 0;

        int thirdMaxSum = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                if (line.isEmpty()) {
                    if (currentSum > firstMaxSum) {
                        thirdMaxSum = secondMaxSum;

                        secondMaxSum = firstMaxSum;

                        firstMaxSum = currentSum;
                    } else if (currentSum > secondMaxSum) {
                        thirdMaxSum = secondMaxSum;

                        secondMaxSum = currentSum;
                    } else if (currentSum > thirdMaxSum) {
                        thirdMaxSum = currentSum;
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

        if (currentSum > firstMaxSum) {
            thirdMaxSum = secondMaxSum;

            secondMaxSum = firstMaxSum;

            firstMaxSum = currentSum;
        } else if (currentSum > secondMaxSum) {
            thirdMaxSum = secondMaxSum;

            secondMaxSum = currentSum;
        } else if (currentSum > thirdMaxSum) {
            thirdMaxSum = currentSum;
        } //end if

        int topThreeSum = firstMaxSum + secondMaxSum + thirdMaxSum;

        System.out.printf("Top Three Sum: %d%n", topThreeSum);
    } //main
}