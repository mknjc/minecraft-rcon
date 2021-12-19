package io.graversen.minecraft.rcon.commands.tellraw;

public record TextContent(
        String text,
        boolean bold,
        boolean italic,
        boolean underlined,
        boolean striketrough,
        boolean obfuscated,
        String color) {


    public TextContent(TellRawCommand tellRawCommand) {
        this(
                tellRawCommand.getText(),
                tellRawCommand.isBold(),
                tellRawCommand.isItalic(),
                tellRawCommand.isUnderlined(),
                tellRawCommand.isStriketrough(),
                tellRawCommand.isObfuscated(),
                tellRawCommand.getColor());
    }
}
