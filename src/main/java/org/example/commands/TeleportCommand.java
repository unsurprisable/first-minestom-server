package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.location.RelativeVec;

public class TeleportCommand extends Command {
    public TeleportCommand() {
        super("tp");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Incorrect usage: /tp <position>", NamedTextColor.RED));
        });

        var positionArgument = ArgumentType.RelativeBlockPosition("position");

        addSyntax((sender, context) -> {
           final RelativeVec relPosition = context.get(positionArgument);

           if (!(sender instanceof Player player)) return;

           final Pos position = Pos.fromPoint(relPosition.from(player.getPosition()));
           player.teleport(position);
        }, positionArgument);
    }
}
