package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.example.items.SimpleGunItem;

public class GunCommand extends Command {

    public GunCommand() {
        super("gun");

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) return;

            player.setItemInMainHand(new SimpleGunItem().getItemStack());
        });

    }
}
