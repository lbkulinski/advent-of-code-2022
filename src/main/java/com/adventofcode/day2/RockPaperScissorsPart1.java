package com.adventofcode.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public final class RockPaperScissorsPart1 {
    private static int getRoundScore(String playerLetter, String opponentLetter) {
        Objects.requireNonNull(playerLetter);

        Objects.requireNonNull(opponentLetter);

        Map<String, Integer> letterToPointValue = Map.of(
            "X", 1,
            "Y", 2,
            "Z", 3
        );

        if (!letterToPointValue.containsKey(playerLetter)) {
            throw new IllegalStateException();
        } //end if

        int roundScore = letterToPointValue.get(playerLetter);

        roundScore += switch (opponentLetter) {
            case "A" -> switch (playerLetter) {
                case "X" -> 3;
                case "Y" -> 6;
                case "Z" -> 0;
                default -> throw new IllegalStateException();
            };
            case "B" -> switch (playerLetter) {
                case "X" -> 0;
                case "Y" -> 3;
                case "Z" -> 6;
                default -> throw new IllegalStateException();
            };
            case "C" -> switch (playerLetter) {
                case "X" -> 6;
                case "Y" -> 0;
                case "Z" -> 3;
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

                totalScore += RockPaperScissorsPart1.getRoundScore(playerLetter, opponentLetter);

                line = reader.readLine();
            } //end while
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        System.out.printf("Total Score: %d%n", totalScore);
    } //main
}