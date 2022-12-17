package com.adventofcode.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CathodeRayTubePart2 {
    private static void printPixel(int cycle, int registerValue, char[][] screen) {
        Objects.requireNonNull(screen);

        int columnIndex = (cycle - 1) % 40;

        int difference = Math.abs(columnIndex - registerValue);

        char character = (difference <= 1) ? '#' : '.';

        int rowIndex = (cycle - 1) / 40;

        screen[rowIndex][columnIndex] = character;
    } //printPixel

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

        char[][] screen = new char[6][40];

        for (String line : lines) {
            cycle++;

            CathodeRayTubePart2.printPixel(cycle, registerValue, screen);

            if (line.equalsIgnoreCase("noop")) {
                continue;
            } //end if

            Matcher matcher = pattern.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalStateException();
            } //end if

            cycle++;

            CathodeRayTubePart2.printPixel(cycle, registerValue, screen);

            String operandString = matcher.group(1);

            int operand = Integer.parseInt(operandString);

            registerValue += operand;
        } //end for

        System.out.println("Message:");

        Arrays.stream(screen)
              .map(String::new)
              .forEach(System.out::println);
    } //main
}