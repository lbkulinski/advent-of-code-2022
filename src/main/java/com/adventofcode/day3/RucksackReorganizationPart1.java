package com.adventofcode.day3;

import java.util.Set;
import java.util.Objects;
import java.util.HashSet;
import java.util.function.Function;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.io.IOException;

public final class RucksackReorganizationPart1 {
    private static Set<Character> getCharacters(String contents) {
        Objects.requireNonNull(contents);

        Set<Character> characters = new HashSet<>();

        for (char letter : contents.toCharArray()) {
            characters.add(letter);
        } //end for

        return characters;
    } //getCharacters

    private static int getPriority(String line) {
        Objects.requireNonNull(line);

        int length = line.length();

        if ((length % 2) != 0) {
            throw new IllegalStateException();
        } //end if

        int midpoint = length / 2;

        String content0 = line.substring(0, midpoint);

        Set<Character> characters0 = RucksackReorganizationPart1.getCharacters(content0);

        String content1 = line.substring(midpoint);

        Set<Character> characters1 = RucksackReorganizationPart1.getCharacters(content1);

        characters0.retainAll(characters1);

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

        int prioritySum = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                prioritySum += RucksackReorganizationPart1.getPriority(line);

                line = reader.readLine();
            } //end while
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        System.out.printf("Priority Sum: %d%n", prioritySum);
    } //main
}