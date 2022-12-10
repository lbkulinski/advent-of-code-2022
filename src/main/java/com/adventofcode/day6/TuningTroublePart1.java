package com.adventofcode.day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TuningTroublePart1 {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day6/input.txt");

        String datastreamBuffer;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            datastreamBuffer = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        if (datastreamBuffer == null) {
            throw new IllegalStateException();
        } //end if

        for (int i = 0, j = 4; j < datastreamBuffer.length(); i++, j++) {
            String window = datastreamBuffer.substring(i, j);

            Set<Character> uniqueCharacters = window.chars()
                                                    .mapToObj(character -> (char) character)
                                                    .collect(Collectors.toUnmodifiableSet());

            int expectedSize = 4;

            if (uniqueCharacters.size() == expectedSize) {
                System.out.printf("First Marker: %d%n", j);

                break;
            } //end if
        } //end for
    } //main
}