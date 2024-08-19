package org.example.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.example.items.CustomItemManager;
import org.example.items.CustomItemManager.CustomItemMaterial;

public class CustomItemCommand extends Command {
    public CustomItemCommand() {
        super("item");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Incorrect usage: /item <item>"));
        });

        var materialArgument = ArgumentType.Enum("item", CustomItemMaterial.class);

        addSyntax((sender, context) -> {
            final CustomItemMaterial material = context.get(materialArgument);

            if (!(sender instanceof Player player)) return;

            player.setItemInMainHand(CustomItemManager.getCustomItemFromMaterial(material).getItemStack());
        }, materialArgument);

    }
}
