package com.jzielinski;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzielinski.controller.FileController;
import com.jzielinski.core.SimulationEngine;
import com.jzielinski.domain.dto.CommandListWrapper;

import java.util.ArrayList;


public class Main {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java -jar app.jar <input.json> <output.json>");
            System.exit(1);
        }

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {

            FileController fileController = new FileController(args[0], args[1]);
            CommandListWrapper commands = fileController.readInput();

            SimulationEngine simulationEngine = new SimulationEngine(commands.getCommands());
            simulationEngine.runSimulation();

            fileController.writeOutput(simulationEngine.getResult());

        } catch (JsonMappingException e) {
            System.err.println("Error reading input.json: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

    }

    private static <T> void printJSON(ArrayList<T> arr) throws JsonProcessingException {
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arr);
        System.out.println(json);
    }
}