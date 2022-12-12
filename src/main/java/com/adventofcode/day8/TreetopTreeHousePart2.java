package com.adventofcode.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class TreetopTreeHousePart2 {
    private static int[][] getGrid(List<String> lines) {
        Objects.requireNonNull(lines);

        int linesSize = lines.size();

        int[][] grid = new int[linesSize][];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            char[] characters = line.toCharArray();

            grid[i] = new int[characters.length];

            for (int j = 0; j < characters.length; j++) {
                char character = characters[j];

                int radix = 10;

                grid[i][j] = Character.digit(character, radix);
            } //end for
        } //end for

        return grid;
    } //getGrid

    private static int getUpVisibleCount(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        int visibleCount = 0;

        for (int i = row - 1; i >= 0; i--) {
            int currentTree = grid[i][column];

            visibleCount++;

            if (currentTree >= tree) {
                break;
            } //end if
        } //end for

        return visibleCount;
    } //getUpVisibleCount

    private static int getDownVisibleCount(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        int visibleCount = 0;

        for (int i = row + 1; i < grid.length; i++) {
            int currentTree = grid[i][column];

            visibleCount++;

            if (currentTree >= tree) {
                break;
            } //end if
        } //end for

        return visibleCount;
    } //getDownVisibleCount

    private static int getLeftVisibleCount(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        int visibleCount = 0;

        for (int i = column - 1; i >= 0; i--) {
            int currentTree = grid[row][i];

            visibleCount++;

            if (currentTree >= tree) {
                break;
            } //end if
        } //end for

        return visibleCount;
    } //getLeftVisibleCount

    private static int getRightVisibleCount(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        int visibleCount = 0;

        for (int i = column + 1; i < grid[row].length; i++) {
            int currentTree = grid[row][i];

            visibleCount++;

            if (currentTree >= tree) {
                break;
            } //end if
        } //end for

        return visibleCount;
    } //getRightVisibleCount

    private static int getScenicScore(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int upVisibleCount = TreetopTreeHousePart2.getUpVisibleCount(grid, row, column);

        int downVisibleCount = TreetopTreeHousePart2.getDownVisibleCount(grid, row, column);

        int leftVisibleCount = TreetopTreeHousePart2.getLeftVisibleCount(grid, row, column);

        int rightVisibleCount = TreetopTreeHousePart2.getRightVisibleCount(grid, row, column);

        return upVisibleCount * downVisibleCount * leftVisibleCount * rightVisibleCount;
    } //getScenicScore

    private static int getMaxScenicScore(List<String> lines) {
        Objects.requireNonNull(lines);

        int[][] grid = TreetopTreeHousePart2.getGrid(lines);

        if (grid.length == 0) {
            return 0;
        } //end if

        int maxScenicScore = 0;

        for (int i = 1; i < (grid.length - 1); i++) {
            for (int j = 1; j < (grid[i].length - 1); j++) {
                int scenicScore = TreetopTreeHousePart2.getScenicScore(grid, i, j);

                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                } //end if
            } //end for
        } //end for

        return maxScenicScore;
    } //getMaxScenicScore

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day8/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        int maxScenicScore = TreetopTreeHousePart2.getMaxScenicScore(lines);

        System.out.printf("Max Scenic Score: %d%n", maxScenicScore);
    } //main
}