package org.example.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class SimpleGunItem extends CustomItem {

    public SimpleGunItem() {
        super(
            ItemStack.builder(Material.WOODEN_HOE)
            .customName(Component.text("Simple Gun", NamedTextColor.GRAY).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
        );
    }

    @Override
    public void rightClick(PlayerUseItemEvent event) {
        event.getPlayer().sendMessage("gun boom!");
    }
}
