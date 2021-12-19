package io.graversen.minecraft.rcon.commands.tellraw;

import io.graversen.minecraft.rcon.JsonUtils;
import io.graversen.minecraft.rcon.commands.base.ICommand;

import java.util.List;

public class TellRawCompositeCommand implements ICommand {
    private final List<TellRawCommand> tellRawCommands;

    public TellRawCompositeCommand(List<TellRawCommand> tellRawCommands) {
        this.tellRawCommands = tellRawCommands;
    }

    public List<TellRawCommand> getTellRawCommands() {
        return tellRawCommands;
    }

    @Override
    public String command() {
        if (tellRawCommands.isEmpty()) throw new IllegalStateException("Cannot send empty TellRawCompositeCommand");

        return "tellraw " + getTellRawCommands().get(0).getTarget() + " " + JsonUtils.toJson(this);
    }
}
