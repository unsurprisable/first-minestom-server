package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;


public class GiveCommand extends Command {

    public GiveCommand() {
        super("give");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Incorrect usage: /give <item> <amount>", NamedTextColor.RED));
        });

        var itemArgument = ArgumentType.ItemStack("item");
        var amountArgument = ArgumentType.Integer("amount");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) return;
            final ItemStack item = context.get(itemArgument);
            GiveItem(player, item, 1);
        }, itemArgument);

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) return;
            final ItemStack item = context.get(itemArgument);
            final int amount = context.get(amountArgument);
            GiveItem(player, item, amount);
        }, itemArgument, amountArgument);
    }

    private void GiveItem(Player player, ItemStack item, int amount) {
        player.getInventory().setItemInMainHand(item.withAmount(amount));

        player.sendMessage(Component.text("Gave " + amount + " of '" + item.material().name() + "'.", NamedTextColor.GREEN));
    }
}
