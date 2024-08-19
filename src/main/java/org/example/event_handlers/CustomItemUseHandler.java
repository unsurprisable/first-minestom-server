package org.example.event_handlers;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.tag.Tag;
import org.example.items.CustomItem;

import java.util.HashMap;
import java.util.UUID;

public class CustomItemUseHandler {

    private static final HashMap<UUID, CustomItem> uuidCustomItemMap = new HashMap<>();

    public static void hook(GlobalEventHandler handler) {
        handler.addListener(PlayerUseItemEvent.class, event -> {
            if (!event.getItemStack().hasTag(Tag.UUID("uuid"))) return;

            uuidCustomItemMap.get(event.getItemStack().getTag(Tag.UUID("uuid")))
                .rightClick(event);
        });
    }

    public static void register(UUID uuid, CustomItem customItem) {
        uuidCustomItemMap.put(uuid, customItem);
    }
}
