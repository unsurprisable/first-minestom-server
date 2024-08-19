package org.example.items;

import net.kyori.adventure.text.Component;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class CoolFireworkItem extends CustomItem {
    public CoolFireworkItem() {
        super(
            ItemStack.builder(Material.FIREWORK_ROCKET)
                .customName(Component.text("Crazy Cool Firework!!!"))
        );
    }

    @Override
    public void rightClick(PlayerUseItemEvent event) {
        event.getPlayer().sendMessage("firework go boom");
    }
}
