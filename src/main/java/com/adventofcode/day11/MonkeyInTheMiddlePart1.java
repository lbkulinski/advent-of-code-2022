package com.adventofcode.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MonkeyInTheMiddlePart1 {
    private record Monkey(List<Integer> items, ToIntFunction<Integer> operation, IntConsumer test) {
        public Monkey {
            Objects.requireNonNull(items);

            Objects.requireNonNull(operation);

            Objects.requireNonNull(test);

            items = new ArrayList<>(items);
        } //Monkey
    } //Monkey

    private static List<Integer> getItems(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "Starting items: (.+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String itemsString = matcher.group(1);

        String separator = ", ";

        String[] itemStrings = itemsString.split(separator);

        return Arrays.stream(itemStrings)
                     .map(Integer::parseInt)
                     .toList();
    } //getItems

    private static ToIntFunction<Integer> getOperation(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "Operation: new = old ([+\\-*/]) (.*)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String rightOperandString = matcher.group(2);

        String operator = matcher.group(1);

        if (rightOperandString.equalsIgnoreCase("old")) {
            return switch (operator) {
                case "+" -> value -> value + value;
                case "-" -> value -> 0;
                case "*" -> value -> value * value;
                case "/" -> value -> 1;
                default -> throw new IllegalStateException();
            };
        } //end if

        int rightOperand = Integer.parseInt(rightOperandString);

        return switch (operator) {
            case "+" -> value -> value + rightOperand;
            case "-" -> value -> value - rightOperand;
            case "*" -> value -> value * rightOperand;
            case "/" -> value -> value / rightOperand;
            default -> throw new IllegalStateException();
        };
    } //getOperation

    private static IntConsumer getTest(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = """
        Test: divisible by (\\d+)
        \\s{4}If true: throw to monkey (\\d+)
        \\s{4}If false: throw to monkey (\\d+)""";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String divisorString = matcher.group(1);

        int divisor = Integer.parseInt(divisorString);

        String trueIndexString = matcher.group(2);

        int trueIndex = Integer.parseInt(trueIndexString);

        String falseIndexString = matcher.group(3);

        int falseIndex = Integer.parseInt(falseIndexString);

        return value -> {};
    } //getTest

    private static Monkey parseMonkeyDefinition(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        List<Integer> items = MonkeyInTheMiddlePart1.getItems(monkeyDefinition);

        ToIntFunction<Integer> operation = MonkeyInTheMiddlePart1.getOperation(monkeyDefinition);

        IntConsumer test = MonkeyInTheMiddlePart1.getTest(monkeyDefinition);

        return new Monkey(items, operation, test);
    } //parseMonkeyDefinition

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day11/sample.txt");

        String lines;

        try {
            lines = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        lines = lines.trim();

        String regex = "\n\n";

        String[] monkeyDefinitions = lines.split(regex);

        List<Monkey> monkeys = new ArrayList<>();

        for (String monkeyDefinition : monkeyDefinitions) {
            Monkey monkey = MonkeyInTheMiddlePart1.parseMonkeyDefinition(monkeyDefinition);

            monkeys.add(monkey);
        } //end for

        monkeys.forEach(System.out::println);
    } //main
}