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

    private record Result(List<Deque<String>> stacks, List<String> instructions) {
    } //Result

    private static Result getStacksResult(List<String> lines) {
        Objects.requireNonNull(lines);

        int stackCount = SupplyStacksPart1.getStackCount(lines);

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

        return new Result(stacks, instructions);
    } //getStacksResult

    private static int getGroup(Matcher matcher, int group) {
        String groupString = matcher.group(group);

        return Integer.parseInt(groupString);
    } //getGroup

    private static List<Deque<String>> getModifiedStacks(List<String> lines) {
        Objects.requireNonNull(lines);

        Result stacksResult = SupplyStacksPart1.getStacksResult(lines);

        List<String> instructions = stacksResult.instructions();

        String regex = "^move (\\d+) from (\\d+) to (\\d+)$";

        Pattern pattern = Pattern.compile(regex);

        List<Deque<String>> stacks = stacksResult.stacks();

        for (String instruction : instructions) {
            Matcher matcher = pattern.matcher(instruction);

            if (!matcher.matches()) {
                throw new IllegalStateException();
            } //end if

            int crateCount = SupplyStacksPart1.getGroup(matcher, 1);

            int sourceStack = SupplyStacksPart1.getGroup(matcher, 2) - 1;

            int targetStack = SupplyStacksPart1.getGroup(matcher, 3) - 1;

            for (int i = 0; i < crateCount; i++) {
                String crate = stacks.get(sourceStack)
                                     .removeFirst();

                stacks.get(targetStack)
                      .addFirst(crate);
            } //end for
        } //end for

        return stacks;
    } //getModifiedStacks

    private static String getModifiedTopCrates(List<String> lines) {
        Objects.requireNonNull(lines);

        List<Deque<String>> stacks = SupplyStacksPart1.getModifiedStacks(lines);

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

        String topCrates = SupplyStacksPart1.getModifiedTopCrates(lines);

        System.out.printf("Top Crates: %s%n", topCrates);
    } //main
}