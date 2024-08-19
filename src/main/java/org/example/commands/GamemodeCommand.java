package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public class GamemodeCommand extends Command {

    public GamemodeCommand()
    {
        super("gamemode");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Incorrect usage: /gamemode <gamemode>", NamedTextColor.RED));
        });

        var gamemodeArgument = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);

        addSyntax((sender, context) -> {
            final GameMode gamemode = context.get(gamemodeArgument);

            if (!(sender instanceof  Player player)) return;

            player.setGameMode(gamemode);
            player.sendMessage(Component.text("Gamemode set to " + gamemode.name() + ".", NamedTextColor.GREEN));
        }, gamemodeArgument);

    }

}
