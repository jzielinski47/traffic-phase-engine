package com.jzielinski;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzielinski.controller.FileController;
import com.jzielinski.core.SimulationEngine;
import com.jzielinski.domain.dto.CommandListWrapper;

import java.nio.file.Files;


public class Main {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java -jar app.jar <input.json> <output.json>");
            System.exit(1);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            FileController fileController = new FileController(args[0], args[1]);

            CommandListWrapper commands = objectMapper.readValue(Files.newInputStream(fileController.getInput()), CommandListWrapper.class);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands);
            System.out.println(json);

            SimulationEngine simulationEngine = new SimulationEngine(commands.getCommands());
            simulationEngine.runSimulation();

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(Files.newOutputStream(fileController.getOutput()), simulationEngine.getResult());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }


    }
}