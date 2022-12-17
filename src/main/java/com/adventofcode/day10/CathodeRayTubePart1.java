package com.adventofcode.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CathodeRayTubePart1 {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day10/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        String regex = "^addx (.+)$";

        Pattern pattern = Pattern.compile(regex);

        int cycle = 0;

        int registerValue = 1;

        Map<Integer, Integer> cycleToRegisterValue = new HashMap<>();

        for (String line : lines) {
            cycle++;

            if ((cycle % 40) == 20) {
                cycleToRegisterValue.put(cycle, registerValue);
            } //end if

            if (line.equalsIgnoreCase("noop")) {
                continue;
            } //end if

            cycle++;

            if ((cycle % 40) == 20) {
                cycleToRegisterValue.put(cycle, registerValue);
            } //end if

            Matcher matcher = pattern.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalStateException();
            } //end if

            String operandString = matcher.group(1);

            int operand = Integer.parseInt(operandString);

            registerValue += operand;
        } //end for

        int signalStrengthSum = cycleToRegisterValue.entrySet()
                                                    .stream()
                                                    .mapToInt(entry -> entry.getKey() * entry.getValue())
                                                    .sum();

        System.out.printf("Signal Strength Sum: %d%n", signalStrengthSum);
    } //main
}