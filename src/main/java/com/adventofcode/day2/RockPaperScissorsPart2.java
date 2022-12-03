package com.adventofcode.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public final class RockPaperScissorsPart2 {
    private static int getRoundScore(String playerLetter, String opponentLetter) {
        Objects.requireNonNull(playerLetter);

        Objects.requireNonNull(opponentLetter);

        Map<String, Integer> letterToPointValue = Map.of(
            "X", 0,
            "Y", 3,
            "Z", 6
        );

        if (!letterToPointValue.containsKey(playerLetter)) {
            throw new IllegalStateException();
        } //end if

        int roundScore = letterToPointValue.get(playerLetter);

        roundScore += switch (opponentLetter) {
            case "A" -> switch (playerLetter) {
                case "X" -> 3;
                case "Y" -> 1;
                case "Z" -> 2;
                default -> throw new IllegalStateException();
            };
            case "B" -> switch (playerLetter) {
                case "X" -> 1;
                case "Y" -> 2;
                case "Z" -> 3;
                default -> throw new IllegalStateException();
            };
            case "C" -> switch (playerLetter) {
                case "X" -> 2;
                case "Y" -> 3;
                case "Z" -> 1;
                default -> throw new IllegalStateException();
            };
            default -> throw new IllegalStateException();
        };

        return roundScore;
    } //getRoundScore

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day2/input.txt");

        int totalScore = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                String[] letters = line.split("\\s+");

                int expectedLength = 2;

                if (letters.length != expectedLength) {
                    throw new IllegalStateException();
                } //end if

                String playerLetter = letters[1];

                String opponentLetter = letters[0];

                totalScore += RockPaperScissorsPart2.getRoundScore(playerLetter, opponentLetter);

                line = reader.readLine();
            } //end while
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        System.out.printf("Total Score: %d%n", totalScore);
    } //main
}