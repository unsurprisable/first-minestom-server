package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;

public class ClearEntitiesCommand extends Command {
    public ClearEntitiesCommand() {
        super("butcher");

        setDefaultExecutor((sender, context) -> {

            if (!(sender instanceof Player player)) return;

            int entitiesCleared = 0;
            for (Entity entity : player.getInstance().getEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                    entitiesCleared++;
                }
            }
            player.sendMessage(Component.text("Cleared " + entitiesCleared + " entities.", NamedTextColor.GREEN));
        });

    }
}
