package com.jzielinski.domain.dto;

import java.util.ArrayList;

public class CommandListWrapper {
    private ArrayList<Command> commands;

    public CommandListWrapper() {
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }
}