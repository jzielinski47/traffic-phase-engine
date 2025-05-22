package com.jzielinski.domain.dto;

import java.util.List;

public class CommandListWrapper {
    private List<Command> commands;

    public CommandListWrapper() {}

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}