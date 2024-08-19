package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.location.RelativeVec;

public class SetblockCommand extends Command {
    public SetblockCommand() {
        super("setblock");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Incorrect usage: /setblock <position> <block>", NamedTextColor.RED));
        });

        var positionArgument = ArgumentType.RelativeBlockPosition("position");
        var blockArgument = ArgumentType.BlockState("block");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) return;

            final RelativeVec position = context.get(positionArgument);
            final Block block = context.get(blockArgument);

            player.getInstance().setBlock(position.from(player.getPosition()), block);

        }, positionArgument, blockArgument);

    }
}
