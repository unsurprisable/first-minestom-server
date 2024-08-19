package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.example.InventoryGame;

public class InventoryGameCommand extends Command {
    public InventoryGameCommand() {
        super("inventory");

        setDefaultExecutor((sender, context) -> {

            if (!(sender instanceof Player player)) return;

            new InventoryGame(player);

        });
    }
}
