package com.jzielinski.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController {
    protected Path input;
    protected Path output;

    public FileController(String input, String output) {
        if (!isValidJsonPath(input)) {
            throw new IllegalArgumentException("Invalid input file path: " + input);
        }

        if (!isValidJsonPath(output)) {
            throw new IllegalArgumentException("Invalid output file path: " + output);
        }

        this.input =  Paths.get(input);
        this.output = Paths.get(output);
    }

    public Path getInput() {
        return input;
    }

    public Path getOutput() {
        return output;
    }

    private boolean isValidJsonPath(String path) {
        return path != null && !path.trim().isEmpty() && path.toLowerCase().endsWith(".json");
    }
}
