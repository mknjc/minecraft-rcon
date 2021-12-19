package io.graversen.minecraft.rcon.commands;

import io.graversen.minecraft.rcon.commands.base.ICommand;

public class DifficultyCommand implements ICommand {
    private final String difficulty;

    public DifficultyCommand(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String command() {
        return "difficulty " + getDifficulty();
    }
}
