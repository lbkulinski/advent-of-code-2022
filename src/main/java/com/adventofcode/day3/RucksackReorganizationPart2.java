package com.adventofcode.day3;

import java.util.Set;
import java.util.Objects;
import java.util.HashSet;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.io.IOException;

public final class RucksackReorganizationPart2 {
    private static Set<Character> getCharacters(String contents) {
        Objects.requireNonNull(contents);

        Set<Character> characters = new HashSet<>();

        for (char letter : contents.toCharArray()) {
            characters.add(letter);
        } //end for

        return characters;
    } //getCharacters

    private static int getPriority(String line0, String line1, String line2) {
        Objects.requireNonNull(line0);

        Objects.requireNonNull(line1);

        Objects.requireNonNull(line2);

        Set<Character> characters0 = RucksackReorganizationPart2.getCharacters(line0);

        Set<Character> characters1 = RucksackReorganizationPart2.getCharacters(line1);

        Set<Character> characters2 = RucksackReorganizationPart2.getCharacters(line2);

        characters0.retainAll(characters1);

        characters0.retainAll(characters2);

        int priority = 0;

        for (Character character : characters0) {
            if ((character >= 97) && (character <= 122)) {
                priority += character - 96;
            } else if ((character >= 65) && (character <= 90)) {
                priority += character - 38;
            } else {
                throw new IllegalStateException();
            } //end if
        } //end for

        return priority;
    } //getPriority

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day3/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        int prioritySum = 0;

        for (int i = 0, j = 3; j <= lines.size(); i += 3, j += 3) {
            List<String> subList = lines.subList(i, j);

            int expectedSize = 3;

            if (subList.size() != expectedSize) {
                throw new IllegalStateException();
            } //end if

            String line0 = subList.get(0);

            String line1 = subList.get(1);

            String line2 = subList.get(2);

            prioritySum += RucksackReorganizationPart2.getPriority(line0, line1, line2);
        } //end for

        System.out.printf("Priority Sum: %d%n", prioritySum);
    } //main
}