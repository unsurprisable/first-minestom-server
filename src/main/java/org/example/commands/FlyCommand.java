package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly");

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) return;

            player.setAllowFlying(!player.isAllowFlying());

            String message = player.isAllowFlying() ? "Flight toggled to ON" : "Flight toggled to OFF";
            player.sendMessage(Component.text(message, NamedTextColor.GREEN));
        });
    }

}
