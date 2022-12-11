package com.adventofcode.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NoSpaceLeftOnDevicePart2 {
    private enum Type {
        FILE,

        DIRECTORY
    } //Type

    private static final class TreeNode {
        Type type;

        TreeNode parent;

        String name;

        int size;

        Set<TreeNode> children;

        public TreeNode(Type type, TreeNode parent, String name, int size, Set<TreeNode> children) {
            this.type = type;

            this.parent = parent;

            this.name = name;

            this.size = size;

            this.children = children;
        } //TreeNode

        public TreeNode(Type type, TreeNode parent, String name, int size) {
            this.type = type;

            this.parent = parent;

            this.name = name;

            this.size = size;

            this.children = new HashSet<>();
        } //TreeNode

        public TreeNode(Type type, TreeNode parent, String name) {
            this.type = type;

            this.parent = parent;

            this.name = name;

            this.size = 0;

            this.children = new HashSet<>();
        } //TreeNode

        public int getSize() {
            return this.size;
        } //getSize

        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.name, this.size);
        } //hashCode

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof TreeNode treeNode)) {
                return false;
            } //end if

            boolean equal = this.type == treeNode.type;

            equal &= Objects.equals(this.name, treeNode.name);

            equal &= (this.size == treeNode.size);

            return equal;
        } //equals

        @Override
        public String toString() {
            return "TreeNode[type=%s, name=%s, size=%d]".formatted(this.type, this.name, this.size);
        } //toString
    } //TreeNode

    private final TreeNode root;

    private TreeNode currentNode;

    private SortedSet<TreeNode> deletionCandidates;

    public NoSpaceLeftOnDevicePart2() {
        Set<TreeNode> children = new HashSet<>();

        this.currentNode = new TreeNode(Type.DIRECTORY, null, "/", 0, children);

        this.root = this.currentNode;
    } //NoSpaceLeftOnDevicePart1

    private void changeDirectory(String directory) {
        switch (directory) {
            case "/" -> {
                while (this.currentNode.parent != null) {
                    this.currentNode = this.currentNode.parent;
                } //end while
            } //case "/"
            case ".." -> {
                if (this.currentNode.parent != null) {
                    this.currentNode = this.currentNode.parent;
                } //end if
            } //case ".."
            default -> {
                Set<TreeNode> children = this.currentNode.children;

                for (TreeNode child : children) {
                    if (child.name.equals(directory) && (child.type == Type.DIRECTORY)) {
                        this.currentNode = child;

                        return;
                    } //end if
                } //end for

                throw new IllegalStateException();
            } //default
        } //end switch
    } //changeDirectory

    private void createTreeNode(String line) {
        String directoryRegex = "dir (.+)";

        Pattern directoryPattern = Pattern.compile(directoryRegex);

        Matcher directoryMatcher = directoryPattern.matcher(line);

        if (directoryMatcher.matches()) {
            String name = directoryMatcher.group(1);

            TreeNode directory = new TreeNode(Type.DIRECTORY, this.currentNode, name);

            this.currentNode.children.add(directory);

            return;
        } //end if

        String fileRegex = "(\\d+) (.+)";

        Pattern filePattern = Pattern.compile(fileRegex);

        Matcher fileMatcher = filePattern.matcher(line);

        if (fileMatcher.matches()) {
            String sizeString = fileMatcher.group(1);

            int size = Integer.parseInt(sizeString);

            String name = fileMatcher.group(2);

            TreeNode file = new TreeNode(Type.FILE, this.currentNode, name, size);

            this.currentNode.children.add(file);

            TreeNode node = this.currentNode;

            while (node != null) {
                node.size = Math.addExact(node.size, size);

                node = node.parent;
            } //end while
        } //end if
    } //createTreeNode

    private void getDirectoryToDeleteHelper(TreeNode currentNode) {
        int minimumSize = 30_000_000 - (70_000_000 - this.root.size);

        if (currentNode.size >= minimumSize) {
            this.deletionCandidates.add(currentNode);
        } //end if

        for (TreeNode child : currentNode.children) {
            if (child.type != Type.DIRECTORY) {
                continue;
            } //end if

            this.getDirectoryToDeleteHelper(child);
        } //end for
    } //getDirectoryToDeleteHelper

    private TreeNode getDirectoryToDelete(List<String> lines) {
        String cdRegex = "^\\$ cd (.+)$";

        Pattern cdPattern = Pattern.compile(cdRegex);

        for (String line : lines) {
            Matcher cdMatcher = cdPattern.matcher(line);

            if (cdMatcher.matches()) {
                String directory = cdMatcher.group(1);

                this.changeDirectory(directory);

                continue;
            } //end if

            if (!line.startsWith("$")) {
                this.createTreeNode(line);
            } //end if
        } //end for

        this.deletionCandidates = new TreeSet<>(Comparator.comparingInt(TreeNode::getSize));

        this.getDirectoryToDeleteHelper(this.root);

        return this.deletionCandidates.first();
    } //getDirectoryToDelete

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/day7/input.txt");

        List<String> lines;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        } //end try catch

        NoSpaceLeftOnDevicePart2 device = new NoSpaceLeftOnDevicePart2();

        TreeNode directory = device.getDirectoryToDelete(lines);

        System.out.printf("Directory Size: %s%n", directory.size);
    } //main
}