package com.adventofcode.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class RopeBridgePart2 {
    private record Knot(int x, int y) {
    } //Knot

    private static final class Rope {
        private Knot head;

        private final Knot[] tails;

        private final Set<Knot> visitedTails;

        public Rope(int knotCount) {
            if (knotCount < 2) {
                throw new IllegalArgumentException("the specified knot count is less than one");
            } //end if

            this.head = new Knot(0, 0);

            int tailCount = knotCount - 1;

            this.tails = new Knot[tailCount];

            for (int i = 0; i < this.tails.length; i++) {
                this.tails[i] = new Knot(0, 0);
            } //end for

            this.visitedTails = new HashSet<>();

            int lastTailIndex = this.tails.length - 1;

            Knot lastTail = this.tails[lastTailIndex];

            this.visitedTails.add(lastTail);
        } //Rope
    } //Rope

    private static void handleRightMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x + 1;

            int newHeadY = rope.head.y;

            rope.head = new Knot(newHeadX, newHeadY);

            Knot currentHead = rope.head;

            for (int j = 0; j < rope.tails.length; j++) {
                int xDifference = Math.abs(currentHead.x - rope.tails[j].x);

                int yDifference = Math.abs(currentHead.y - rope.tails[j].y);

                if ((xDifference <= 1) && (yDifference <= 1)) {
                    break;
                } //end if

                int newTailX;

                if (xDifference == 0) {
                    newTailX = rope.tails[j].x;
                } else if (rope.tails[j].x > currentHead.x) {
                    newTailX = rope.tails[j].x - 1;
                } else {
                    newTailX = rope.tails[j].x + 1;
                } //end if

                int newTailY;

                if ((xDifference == 2) && (yDifference == 0)) {
                    newTailY = rope.tails[j].y;
                } else if (rope.tails[j].y > currentHead.y) {
                    newTailY = rope.tails[j].y - 1;
                } else {
                    newTailY = rope.tails[j].y + 1;
                } //end if

                rope.tails[j] = new Knot(newTailX, newTailY);

                currentHead = rope.tails[j];
            } //end for

            int lastTailIndex = rope.tails.length - 1;

            Knot lastTail = rope.tails[lastTailIndex];

            rope.visitedTails.add(lastTail);
        } //end for
    } //handleRightMoves

    private static void handleLeftMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x - 1;

            int newHeadY = rope.head.y;

            rope.head = new Knot(newHeadX, newHeadY);

            Knot currentHead = rope.head;

            for (int j = 0; j < rope.tails.length; j++) {
                int xDifference = Math.abs(currentHead.x - rope.tails[j].x);

                int yDifference = Math.abs(currentHead.y - rope.tails[j].y);

                if ((xDifference <= 1) && (yDifference <= 1)) {
                    break;
                } //end if

                int newTailX;

                if (xDifference == 0) {
                    newTailX = rope.tails[j].x;
                } else if (rope.tails[j].x > currentHead.x) {
                    newTailX = rope.tails[j].x - 1;
                } else {
                    newTailX = rope.tails[j].x + 1;
                } //end if

                int newTailY;

                if ((xDifference == 2) && (yDifference == 0)) {
                    newTailY = rope.tails[j].y;
                } else if (rope.tails[j].y > currentHead.y) {
                    newTailY = rope.tails[j].y - 1;
                } else {
                    newTailY = rope.tails[j].y + 1;
                } //end if

                rope.tails[j] = new Knot(newTailX, newTailY);

                currentHead = rope.tails[j];
            } //end for

            int lastTailIndex = rope.tails.length - 1;

            Knot lastTail = rope.tails[lastTailIndex];

            rope.visitedTails.add(lastTail);
        } //end for
    } //handleLeftMoves

    private static void handleUpMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x;

            int newHeadY = rope.head.y + 1;

            rope.head = new Knot(newHeadX, newHeadY);

            Knot currentHead = rope.head;

            for (int j = 0; j < rope.tails.length; j++) {
                int xDifference = Math.abs(currentHead.x - rope.tails[j].x);

                int yDifference = Math.abs(currentHead.y - rope.tails[j].y);

                if ((xDifference <= 1) && (yDifference <= 1)) {
                    break;
                } //end if

                int newTailX;

                if ((xDifference == 0) && (yDifference == 2)) {
                    newTailX = rope.tails[j].x;
                } else if (rope.tails[j].x > currentHead.x) {
                    newTailX = rope.tails[j].x - 1;
                } else {
                    newTailX = rope.tails[j].x + 1;
                } //end if

                int newTailY;

                if (yDifference == 0) {
                    newTailY = rope.tails[j].y;
                } else if (rope.tails[j].y > currentHead.y) {
                    newTailY = rope.tails[j].y - 1;
                } else {
                    newTailY = rope.tails[j].y + 1;
                } //end if

                rope.tails[j] = new Knot(newTailX, newTailY);

                currentHead = rope.tails[j];
            } //end for

            int lastTailIndex = rope.tails.length - 1;

            Knot lastTail = rope.tails[lastTailIndex];

            rope.visitedTails.add(lastTail);
        } //end for
    } //handleUpMoves

    private static void handleDownMoves(Rope rope, int count) {
        Objects.requireNonNull(rope);

        for (int i = 0; i < count; i++) {
            int newHeadX = rope.head.x;

            int newHeadY = rope.head.y - 1;

            rope.head = new Knot(newHeadX, newHeadY);

            Knot currentHead = rope.head;

            for (int j = 0; j < rope.tails.length; j++) {
                int xDifference = Math.abs(currentHead.x - rope.tails[j].x);

                int yDifference = Math.abs(currentHead.y - rope.tails[j].y);

                if ((xDifference <= 1) && (yDifference <= 1)) {
                    break;
                } //end if

                int newTailX;

                if ((xDifference == 0) && (yDifference == 2)) {
                    newTailX = rope.tails[j].x;
                } else if (rope.tails[j].x > currentHead.x) {
                    newTailX = rope.tails[j].x - 1;
                } else {
                    newTailX = rope.tails[j].x + 1;
                } //end if

                int newTailY;

                if (yDifference == 0) {
                    newTailY = rope.tails[j].y;
                } else if (rope.tails[j].y > currentHead.y) {
                    newTailY = rope.tails[j].y - 1;
                } else {
                    newTailY = rope.tails[j].y + 1;
                } //end if

                rope.tails[j] = new Knot(newTailX, newTailY);

                currentHead = rope.tails[j];
            } //end for

            int lastTailIndex = rope.tails.length - 1;

            Knot lastTail = rope.tails[lastTailIndex];

            rope.visitedTails.add(lastTail);
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

        int knotCount = 10;

        Rope rope = new Rope(knotCount);

        for (String line : lines) {
            String[] parts = line.split("\\s+");

            int expectedLength = 2;

            if (parts.length != expectedLength) {
                throw new IllegalStateException();
            } //end if

            String direction = parts[0];

            int count = Integer.parseInt(parts[1]);

            switch (direction) {
                case "R" -> RopeBridgePart2.handleRightMoves(rope, count);
                case "L" -> RopeBridgePart2.handleLeftMoves(rope, count);
                case "U" -> RopeBridgePart2.handleUpMoves(rope, count);
                case "D" -> RopeBridgePart2.handleDownMoves(rope, count);
                default -> throw new IllegalStateException();
            } //end switch
        } //end for

        int tailPositions = rope.visitedTails.size();

        System.out.printf("Tail Positions: %d%n", tailPositions);
    } //main
}