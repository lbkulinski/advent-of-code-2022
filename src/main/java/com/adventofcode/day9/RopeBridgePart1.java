package com.adventofcode.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class RopeBridgePart1 {
    private record Knot(int x, int y) {
    } //Knot

    private static final class Rope {
        private Knot head;

        private Knot tail;

        private final Set<Knot> visitedTails;

        public Rope() {
            this.head = new Knot(0, 0);

            this.tail = new Knot(0, 0);

            this.visitedTails = new HashSet<>();

            this.visitedTails.add(this.tail);
        } //Rope
    } //Rope

    private static void handleRightMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x + 1;

            int newHeadY = rope.head.y;

            rope.head = new Knot(newHeadX, newHeadY);

            int xDifference = Math.abs(rope.head.x - rope.tail.x);

            int yDifference = Math.abs(rope.head.y - rope.tail.y);

            if ((xDifference <= 1) && (yDifference <= 1)) {
                continue;
            } //end if

            int newTailX = rope.tail.x + 1;

            int newTailY;

            if ((xDifference == 2) && (yDifference == 0)) {
                newTailY = rope.tail.y;
            } else if (rope.tail.y > rope.head.y) {
                newTailY = rope.tail.y - 1;
            } else {
                newTailY = rope.tail.y + 1;
            } //end if

            rope.tail = new Knot(newTailX, newTailY);

            rope.visitedTails.add(rope.tail);
        } //end for
    } //handleRightMoves

    private static void handleLeftMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x - 1;

            int newHeadY = rope.head.y;

            rope.head = new Knot(newHeadX, newHeadY);

            int xDifference = Math.abs(rope.head.x - rope.tail.x);

            int yDifference = Math.abs(rope.head.y - rope.tail.y);

            if ((xDifference <= 1) && (yDifference <= 1)) {
                continue;
            } //end if

            int newTailX = rope.tail.x - 1;

            int newTailY;

            if ((xDifference == 2) && (yDifference == 0)) {
                newTailY = rope.tail.y;
            } else if (rope.tail.y > rope.head.y) {
                newTailY = rope.tail.y - 1;
            } else {
                newTailY = rope.tail.y + 1;
            } //end if

            rope.tail = new Knot(newTailX, newTailY);

            rope.visitedTails.add(rope.tail);
        } //end for
    } //handleLeftMoves

    private static void handleUpMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x;

            int newHeadY = rope.head.y + 1;

            rope.head = new Knot(newHeadX, newHeadY);

            int xDifference = Math.abs(rope.head.x - rope.tail.x);

            int yDifference = Math.abs(rope.head.y - rope.tail.y);

            if ((xDifference <= 1) && (yDifference <= 1)) {
                continue;
            } //end if

            int newTailX;

            if ((xDifference == 0) && (yDifference == 2)) {
                newTailX = rope.tail.x;
            } else if (rope.tail.x > rope.head.x) {
                newTailX = rope.tail.x - 1;
            } else {
                newTailX = rope.tail.x + 1;
            } //end if

            int newTailY = rope.tail.y + 1;

            rope.tail = new Knot(newTailX, newTailY);

            rope.visitedTails.add(rope.tail);
        } //end for
    } //handleUpMoves

    private static void handleDownMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x;

            int newHeadY = rope.head.y - 1;

            rope.head = new Knot(newHeadX, newHeadY);

            int xDifference = Math.abs(rope.head.x - rope.tail.x);

            int yDifference = Math.abs(rope.head.y - rope.tail.y);

            if ((xDifference <= 1) && (yDifference <= 1)) {
                continue;
            } //end if

            int newTailX;

            if ((xDifference == 0) && (yDifference == 2)) {
                newTailX = rope.tail.x;
            } else if (rope.tail.x > rope.head.x) {
                newTailX = rope.tail.x - 1;
            } else {
                newTailX = rope.tail.x + 1;
            } //end if

            int newTailY = rope.tail.y - 1;

            rope.tail = new Knot(newTailX, newTailY);

            rope.visitedTails.add(rope.tail);
        } //end for
    } //handleDownMoves

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day9/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        Rope rope = new Rope();

        for (String line : lines) {
            String[] parts = line.split("\\s+");

            int expectedLength = 2;

            if (parts.length != expectedLength) {
                throw new IllegalStateException();
            } //end if

            String direction = parts[0];

            int count = Integer.parseInt(parts[1]);

            switch (direction) {
                case "R" -> {
                    RopeBridgePart1.handleRightMoves(rope, count);
                } //case "R"
                case "L" -> {
                    RopeBridgePart1.handleLeftMoves(rope, count);
                } //case "L"
                case "U" -> {
                    RopeBridgePart1.handleUpMoves(rope, count);
                } //case "U"
                case "D" -> {
                    RopeBridgePart1.handleDownMoves(rope, count);
                } //case "D"
                default -> throw new IllegalStateException();
            } //end switch
        } //end for

        int tailPositions = rope.visitedTails.size();

        System.out.printf("Tail Positions: %d%n", tailPositions);
    } //main
}