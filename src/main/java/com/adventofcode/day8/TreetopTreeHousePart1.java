package com.adventofcode.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TreetopTreeHousePart1 {
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

    private static boolean checkUp(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        for (int i = row - 1; i >= 0; i--) {
            int currentTree = grid[i][column];

            if (currentTree >= tree) {
                return false;
            } //end if
        } //end for

        return true;
    } //checkUp

    private static boolean checkDown(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        for (int i = row + 1; i < grid.length; i++) {
            int currentTree = grid[i][column];

            if (currentTree >= tree) {
                return false;
            } //end if
        } //end for

        return true;
    } //checkDown

    private static boolean checkLeft(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        for (int i = column - 1; i >= 0; i--) {
            int currentTree = grid[row][i];

            if (currentTree >= tree) {
                return false;
            } //end if
        } //end for

        return true;
    } //checkLeft

    private static boolean checkRight(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        int tree = grid[row][column];

        for (int i = column + 1; i < grid[row].length; i++) {
            int currentTree = grid[row][i];

            if (currentTree >= tree) {
                return false;
            } //end if
        } //end for

        return true;
    } //checkRight

    private static boolean checkVisibility(int[][] grid, int row, int column) {
        Objects.requireNonNull(grid);

        boolean visible = TreetopTreeHousePart1.checkUp(grid, row, column);

        visible |= TreetopTreeHousePart1.checkDown(grid, row, column);

        visible |= TreetopTreeHousePart1.checkLeft(grid, row, column);

        visible |= TreetopTreeHousePart1.checkRight(grid, row, column);

        return visible;
    } //checkRight

    private static int getVisibleTreeCount(List<String> lines) {
        Objects.requireNonNull(lines);

        int[][] grid = TreetopTreeHousePart1.getGrid(lines);

        if (grid.length == 0) {
            return 0;
        } //end if

        int visibleTreeCount = (grid.length * 2) + (grid[0].length * 2) - 4;

        for (int i = 1; i < (grid.length - 1); i++) {
            for (int j = 1; j < (grid[i].length - 1); j++) {
                boolean visible = TreetopTreeHousePart1.checkVisibility(grid, i, j);

                if (visible) {
                    visibleTreeCount++;
                } //end if
            } //end for
        } //end for

        return visibleTreeCount;
    } //getVisibleTreeCount

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day8/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        int visibleTreeCount = TreetopTreeHousePart1.getVisibleTreeCount(lines);

        System.out.printf("Visible Tree Count: %d%n", visibleTreeCount);
    } //main
}