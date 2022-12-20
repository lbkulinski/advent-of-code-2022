package com.adventofcode.day11;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MonkeyInTheMiddlePart2 {
    private record Monkey(Queue<BigInteger> items, Function<BigInteger, BigInteger> operation,
        Predicate<BigInteger> test, int trueIndex, int falseIndex) {
        public Monkey {
            Objects.requireNonNull(items);

            Objects.requireNonNull(operation);

            Objects.requireNonNull(test);

            items = new ArrayDeque<>(items);
        } //Monkey
    } //Monkey

    private static Queue<BigInteger> getItems(String monkeyDefinition) {
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
                     .map(BigInteger::new)
                     .collect(Collectors.toCollection(ArrayDeque::new));
    } //getItems

    private static BigInteger getTestDivisor(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "Test: divisible by (\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String divisorString = matcher.group(1);

        return new BigInteger(divisorString);
    } //getTestDivisor

    private static Function<BigInteger, BigInteger> getOperation(String monkeyDefinition, BigInteger divisor) {
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
                case "+" -> value -> value.add(value)
                                          .mod(divisor);
                case "-" -> value -> BigInteger.ZERO;
                case "*" -> value -> value.multiply(value)
                                          .mod(divisor);
                case "/" -> value -> BigInteger.ONE.mod(divisor);
                default -> throw new IllegalStateException();
            };
        } //end if

        BigInteger rightOperand = new BigInteger(rightOperandString);

        return switch (operator) {
            case "+" -> value -> value.add(rightOperand)
                                      .mod(divisor);
            case "-" -> value -> value.subtract(rightOperand)
                                      .mod(divisor);
            case "*" -> value -> value.multiply(rightOperand)
                                      .mod(divisor);
            case "/" -> value -> value.divide(rightOperand)
                                      .mod(divisor);
            default -> throw new IllegalStateException();
        };
    } //getOperation

    private static Predicate<BigInteger> getTest(String monkeyDefinition) {
        Objects.requireNonNull(monkeyDefinition);

        String regex = "Test: divisible by (\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(monkeyDefinition);

        if (!matcher.find()) {
            throw new IllegalStateException();
        } //end if

        String divisorString = matcher.group(1);

        BigInteger divisor = new BigInteger(divisorString);

        return value -> {
            BigInteger remainder = value.mod(divisor);

            return remainder.equals(BigInteger.ZERO);
        };
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

    private static Monkey parseMonkeyDefinition(String monkeyDefinition, BigInteger divisor) {
        Objects.requireNonNull(monkeyDefinition);

        Queue<BigInteger> items = MonkeyInTheMiddlePart2.getItems(monkeyDefinition);

        Function<BigInteger, BigInteger> operation = MonkeyInTheMiddlePart2.getOperation(monkeyDefinition, divisor);

        Predicate<BigInteger> test = MonkeyInTheMiddlePart2.getTest(monkeyDefinition);

        int trueIndex = MonkeyInTheMiddlePart2.getTrueIndex(monkeyDefinition);

        int falseIndex = MonkeyInTheMiddlePart2.getFalseIndex(monkeyDefinition);

        return new Monkey(items, operation, test, trueIndex, falseIndex);
    } //parseMonkeyDefinition

    private static void commenceRound(List<Monkey> monkeys, Map<Integer, BigInteger> indexToInspectionCount) {
        Objects.requireNonNull(monkeys);

        Objects.requireNonNull(indexToInspectionCount);

        for (int i = 0; i < monkeys.size(); i++) {
            Monkey monkey = monkeys.get(i);

            Queue<BigInteger> items = monkey.items();

            Function<BigInteger, BigInteger> operation = monkey.operation();

            Predicate<BigInteger> test = monkey.test();

            while (!items.isEmpty()) {
                indexToInspectionCount.compute(i, (key, value) -> {
                    if (value == null) {
                        return BigInteger.ONE;
                    } //end if

                    return value.add(BigInteger.ONE);
                });

                BigInteger item = items.remove();

                BigInteger transformedItem = operation.apply(item);

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

    private static BigInteger getMonkeyBusiness(String lines) {
        Objects.requireNonNull(lines);

        String regex = "\n\n";

        String[] monkeyDefinitions = lines.split(regex);

        BigInteger divisor = BigInteger.ONE;

        for (String monkeyDefinition : monkeyDefinitions) {
            BigInteger testDivisor = MonkeyInTheMiddlePart2.getTestDivisor(monkeyDefinition);

            divisor = divisor.multiply(testDivisor);
        } //end for

        List<Monkey> monkeys = new ArrayList<>();

        for (String monkeyDefinition : monkeyDefinitions) {
            Monkey monkey = MonkeyInTheMiddlePart2.parseMonkeyDefinition(monkeyDefinition, divisor);

            monkeys.add(monkey);
        } //end for

        int monkeyCount = monkeys.size();

        Map<Integer, BigInteger> indexToInspectionCount = HashMap.newHashMap(monkeyCount);

        for (int i = 0; i < 10_000; i++) {
            MonkeyInTheMiddlePart2.commenceRound(monkeys, indexToInspectionCount);
        } //end for

        Comparator<BigInteger> descendingComparator = Comparator.reverseOrder();

        long maxInspectionCounts = 2L;

        return indexToInspectionCount.values()
                                     .stream()
                                     .sorted(descendingComparator)
                                     .limit(maxInspectionCounts)
                                     .reduce(BigInteger::multiply)
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

        BigInteger monkeyBusiness = MonkeyInTheMiddlePart2.getMonkeyBusiness(lines);

        System.out.printf("Monkey Business: %s%n", monkeyBusiness);
    } //main
}