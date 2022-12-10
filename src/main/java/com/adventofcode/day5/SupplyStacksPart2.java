package com.adventofcode.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SupplyStacksPart2 {
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

    private record StartingStacks(List<Deque<String>> stacks, List<String> instructions) {
    } //StartingStacks

    private static StartingStacks getStartingStacks(List<String> lines) {
        Objects.requireNonNull(lines);

        int stackCount = SupplyStacksPart2.getStackCount(lines);

        List<Deque<String>> stacks = new ArrayList<>(stackCount);

        for (int i = 0; i < stackCount; i++) {
            Deque<String> deque = new ArrayDeque<>();

            stacks.add(deque);
        } //end for

        String regex = ".(.).\\s?";

        Pattern pattern = Pattern.compile(regex);

        Integer instructionsIndex = null;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String indicesRegex = "\\s(\\d\\s+)+";

            if (line.matches(indicesRegex)) {
                instructionsIndex = i + 2;

                break;
            } //end if

            Matcher matcher = pattern.matcher(line);

            List<String> results = matcher.results()
                                          .map(matchResult -> matchResult.group(1))
                                          .toList();

            for (int j = 0; j < results.size(); j++) {
                String crate = results.get(j);

                if (crate.isBlank()) {
                    continue;
                } //end if

                stacks.get(j)
                      .addLast(crate);
            } //end for
        } //end for

        if (instructionsIndex == null) {
            throw new IllegalStateException();
        } //end if

        int endIndex = lines.size();

        List<String> instructions = lines.subList(instructionsIndex, endIndex);

        return new StartingStacks(stacks, instructions);
    } //getStartingStacks

    private static int getGroup(Matcher matcher, int group) {
        String groupString = matcher.group(group);

        return Integer.parseInt(groupString);
    } //getGroup

    private static List<Deque<String>> getModifiedStacks(List<String> lines) {
        Objects.requireNonNull(lines);

        StartingStacks startingStacks = SupplyStacksPart2.getStartingStacks(lines);

        List<String> instructions = startingStacks.instructions();

        String regex = "^move (\\d+) from (\\d+) to (\\d+)$";

        Pattern pattern = Pattern.compile(regex);

        List<Deque<String>> stacks = startingStacks.stacks();

        for (String instruction : instructions) {
            Matcher matcher = pattern.matcher(instruction);

            if (!matcher.matches()) {
                throw new IllegalStateException();
            } //end if

            int crateCount = SupplyStacksPart2.getGroup(matcher, 1);

            int sourceStack = SupplyStacksPart2.getGroup(matcher, 2) - 1;

            List<String> removedCrates = new ArrayList<>();

            for (int i = 0; i < crateCount; i++) {
                String crate = stacks.get(sourceStack)
                                     .removeFirst();

                removedCrates.add(crate);
            } //end for

            Collections.reverse(removedCrates);

            int targetStack = SupplyStacksPart2.getGroup(matcher, 3) - 1;

            for (String crate : removedCrates) {
                stacks.get(targetStack)
                      .addFirst(crate);
            } //end for
        } //end for

        return stacks;
    } //getModifiedStacks

    private static String getModifiedTopCrates(List<String> lines) {
        Objects.requireNonNull(lines);

        List<Deque<String>> stacks = SupplyStacksPart2.getModifiedStacks(lines);

        StringBuilder stringBuilder = new StringBuilder();

        for (Deque<String> stack : stacks) {
            String topCrate = stack.peekFirst();

            stringBuilder.append(topCrate);
        } //end for

        return stringBuilder.toString();
    } //getModifiedTopCrates

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day5/input.txt");

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

        String topCrates = SupplyStacksPart2.getModifiedTopCrates(lines);

        System.out.printf("Top Crates: %s%n", topCrates);
    } //main
}