package com.jzielinski.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzielinski.domain.dto.CommandListWrapper;
import com.jzielinski.domain.dto.SimulationResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController {
    private final Path input;
    private final Path output;
    private ObjectMapper objectMapper = new ObjectMapper();

    public FileController(String input, String output) {
        if (isNotValidJsonPath(input)) {
            throw new IllegalArgumentException("Invalid input file path: " + input);
        }

        if (isNotValidJsonPath(output)) {
            throw new IllegalArgumentException("Invalid output file path: " + output);
        }

        this.input = Paths.get(input);
        this.output = Paths.get(output);
    }

    public CommandListWrapper readInput() throws IOException {
        return objectMapper.readValue(Files.newInputStream(input), CommandListWrapper.class);
    }

    public void writeOutput(SimulationResult result) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(Files.newOutputStream(output), result);
    }

    public Path getInput() {
        return input;
    }

    public Path getOutput() {
        return output;
    }

    private boolean isNotValidJsonPath(String path) {
        return path == null || path.trim().isEmpty() || !path.toLowerCase().endsWith(".json");
    }
}
