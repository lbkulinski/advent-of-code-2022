package com.adventofcode.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MonkeyInTheMiddlePart1 {
    private record Monkey(Queue<Integer> items, ToIntFunction<Integer> operation, IntPredicate test, int trueIndex,
        int falseIndex) {
        public Monkey {
            Objects.requireNonNull(items);

            Objects.requireNonNull(operation);

            Objects.requireNonNull(test);

            items = new ArrayDeque<>(items);
        } //Monkey
    } //Monkey

    private static Queue<Integer> getItems(String monkeyDefinition) {
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
                     .collect(Collectors.toCollection(ArrayDeque::new));
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
                case "+" -> value -> (value + value) / 3;
                case "-", "/" -> value -> 0;
                case "*" -> value -> (value * value) / 3;
                default -> throw new IllegalStateException();
            };
        } //end if

        int rightOperand = Integer.parseInt(rightOperandString);

        return switch (operator) {
            case "+" -> value -> (value + rightOperand) / 3;
            case "-" -> value -> (value - rightOperand) / 3;
            case "*" -> value -> (value * rightOperand) / 3;
            case "/" -> value -> (value / rightOperand) / 3;
            default -> throw new IllegalStateException();
        };
    } //getOperation

    private static IntPredicate getTest(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "Test: divisible by (\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String divisorString = matcher.group(1);

        int divisor = Integer.parseInt(divisorString);

        return value -> (value % divisor) == 0;
    } //getTest

    private static int getTrueIndex(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "If true: throw to monkey (\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String trueIndexString = matcher.group(1);

        return Integer.parseInt(trueIndexString);
    } //getTrueIndex

    private static int getFalseIndex(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "If false: throw to monkey (\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String falseIndexString = matcher.group(1);

        return Integer.parseInt(falseIndexString);
    } //getFalseIndex

    private static Monkey parseMonkeyDefinition(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        Queue<Integer> items = MonkeyInTheMiddlePart1.getItems(monkeyDefinition);

        ToIntFunction<Integer> operation = MonkeyInTheMiddlePart1.getOperation(monkeyDefinition);

        IntPredicate test = MonkeyInTheMiddlePart1.getTest(monkeyDefinition);

        int trueIndex = MonkeyInTheMiddlePart1.getTrueIndex(monkeyDefinition);

        int falseIndex = MonkeyInTheMiddlePart1.getFalseIndex(monkeyDefinition);

        return new Monkey(items, operation, test, trueIndex, falseIndex);
    } //parseMonkeyDefinition

    private static void commenceRound(List<Monkey> monkeys, Map<Integer, Integer> indexToInspectionCount) {
        Objects.requireNonNull(monkeys);

        Objects.requireNonNull(indexToInspectionCount);

        for (int i = 0; i < monkeys.size(); i++) {
            Monkey monkey = monkeys.get(i);

            Queue<Integer> items = monkey.items();

            ToIntFunction<Integer> operation = monkey.operation();

            IntPredicate test = monkey.test();

            while (!items.isEmpty()) {
                indexToInspectionCount.compute(i, (key, value) -> (value == null) ? 1 : (value + 1));

                int item = items.remove();

                int transformedItem = operation.applyAsInt(item);

                if (test.test(transformedItem)) {
                    int trueIndex = monkey.trueIndex();

                    Monkey trueMonkey = monkeys.get(trueIndex);

                    trueMonkey.items()
                              .add(transformedItem);

                    continue;
                } //end if

                int falseIndex = monkey.falseIndex();

                Monkey falseMonkey = monkeys.get(falseIndex);

                falseMonkey.items()
                           .add(transformedItem);
            } //end while
        } //end for
    } //commenceRound

    private static int getMonkeyBusiness(String lines) {
        Objects.requireNonNull(lines);

        String regex = "\n\n";

        String[] monkeyDefinitions = lines.split(regex);

        List<Monkey> monkeys = new ArrayList<>();

        for (String monkeyDefinition : monkeyDefinitions) {
            Monkey monkey = MonkeyInTheMiddlePart1.parseMonkeyDefinition(monkeyDefinition);

            monkeys.add(monkey);
        } //end for

        int monkeyCount = monkeys.size();

        Map<Integer, Integer> indexToInspectionCount = HashMap.newHashMap(monkeyCount);

        for (int i = 0; i < 20; i++) {
            MonkeyInTheMiddlePart1.commenceRound(monkeys, indexToInspectionCount);
        } //end for

        Comparator<Integer> descendingComparator = (x, y) -> Integer.compare(y, x);

        long maxInspectionCounts = 2L;

        IntBinaryOperator productOperator = (x, y) -> x * y;

        return indexToInspectionCount.values()
                                     .stream()
                                     .sorted(descendingComparator)
                                     .limit(maxInspectionCounts)
                                     .mapToInt(Integer::intValue)
                                     .reduce(productOperator)
                                     .orElseThrow();
    } //getMonkeyBusiness

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day11/input.txt");

        String lines;

        try {
            lines = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        int monkeyBusiness = MonkeyInTheMiddlePart1.getMonkeyBusiness(lines);

        System.out.printf("Monkey Business: %d%n", monkeyBusiness);
    } //main
}