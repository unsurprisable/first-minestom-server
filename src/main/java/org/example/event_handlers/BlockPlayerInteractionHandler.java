package org.example.event_handlers;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import org.example.block_handlers.ChestHandler;


public class BlockPlayerInteractionHandler {

    public static void hook(GlobalEventHandler handler) {
        handler.addListener(PlayerBlockPlaceEvent.class, event -> {
            Block block = event.getBlock();

            if (block.compare(Block.CHEST)) {
                event.setBlock(Block.CHEST.withHandler(new ChestHandler()));
            }
        });
    }
}
