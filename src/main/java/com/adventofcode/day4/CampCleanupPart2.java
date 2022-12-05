package com.adventofcode.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CampCleanupPart2 {
    private record Range(int start, int end) {
        public Range {
            if (start > end) {
                throw new IllegalArgumentException();
            } //end if
        } //Range

        public boolean overlaps(Range other) {
            Objects.requireNonNull(other);

            return (this.start <= other.end) && (this.end >= other.start);
        } //overlaps
    } //Range

    private static int parseInt(Matcher matcher, int groupIndex) {
        Objects.requireNonNull(matcher);

        int groupCount = matcher.groupCount();

        if (groupIndex > groupCount) {
            throw new IllegalArgumentException();
        } //end if

        String groupString = matcher.group(groupIndex);

        return Integer.parseInt(groupString);
    } //parseInt

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day4/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        String regex = "^(\\d+)-(\\d+),(\\d+)-(\\d+)$";

        Pattern pattern = Pattern.compile(regex);

        int overlapCount = 0;

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalStateException();
            } //end if

            int start0 = CampCleanupPart2.parseInt(matcher, 1);

            int end0 = CampCleanupPart2.parseInt(matcher, 2);

            Range range0 = new Range(start0, end0);

            int start1 = CampCleanupPart2.parseInt(matcher, 3);

            int end1 = CampCleanupPart2.parseInt(matcher, 4);

            Range range1 = new Range(start1, end1);

            if (range0.overlaps(range1)) {
                overlapCount++;
            } //end if
        } //end for

        System.out.printf("Overlap Count: %d%n", overlapCount);
    } //main
}