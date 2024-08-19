package org.example.items;

import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.example.event_handlers.CustomItemUseHandler;

import java.util.UUID;

public class CustomItem {

    private final ItemStack itemStack;

    public CustomItem(ItemStack.Builder itemStackBuilder) {
        UUID itemUuid = UUID.randomUUID();
        this.itemStack = itemStackBuilder
            .set(Tag.UUID("uuid"), itemUuid)
            .build();

        CustomItemUseHandler.register(itemUuid, this);
    }

    public void rightClick(PlayerUseItemEvent event) {
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
