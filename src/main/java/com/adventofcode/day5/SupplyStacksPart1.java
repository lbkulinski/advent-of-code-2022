package com.adventofcode.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SupplyStacksPart1 {
    private static int getStackCount(List<String> lines) {
        Objects.requireNonNull(lines);

        Integer stacksIndex = null;

        String numbersRegex = "^\\s(\\d+\\s+)+$";

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.matches(numbersRegex)) {
                stacksIndex = i;

                break;
            } //end if
        } //end for

        if (stacksIndex == null) {
            throw new IllegalStateException();
        } //end if

        String stacksString = lines.get(stacksIndex)
                                   .strip();

        String spacesRegex = "\\s+";

        String[] stacks = stacksString.split(spacesRegex);

        int lastIndex = stacks.length - 1;

        if (lastIndex < 0) {
            throw new IllegalStateException();
        } //end if

        String stackCountString = stacks[lastIndex];

        return Integer.parseInt(stackCountString);
    } //getStackCount

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day5/sample.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        if (lines.isEmpty()) {
            throw new IllegalStateException();
        } //end while

        int stackCount = SupplyStacksPart1.getStackCount(lines);

        @SuppressWarnings("unchecked")
        Deque<String>[] stacks = (Deque<String>[]) new Deque[stackCount];

        for (int i = 0; i < stackCount; i++) {
            stacks[i] = new ArrayDeque<>();
        } //end for

        System.out.println(stackCount);

        String regex = ".(.).\\s?";

        System.out.println(regex);

        Pattern pattern = Pattern.compile(regex);

        for (String line : lines) {
            System.out.println(line);

            if (line.matches("\\s(\\d\\s+)+")) {
                break;
            } //end if

            Matcher matcher = pattern.matcher(line);

            List<String> results = matcher.results()
                                          .map(matchResult -> matchResult.group(1))
                                          .toList();

            for (int j = 0; j < results.size(); j++) {
                String item = results.get(j);

                if (item.isBlank()) {
                    continue;
                } //end if

                stacks[j].addLast(item);
            } //end for
        } //end for

        Arrays.stream(stacks)
              .forEach(System.out::println);
    } //main
}