package org.example.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LightningWandItem extends CustomItem {
    public LightningWandItem() {
        super(
            ItemStack.builder(Material.STICK)
                .customName(Component.text("Lightning Wand", NamedTextColor.YELLOW).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                .lore(Component.text("Strike down your foes...", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                .glowing()
        );
    }

    @Override
    public void rightClick(PlayerUseItemEvent event) {
        event.setCancelled(true);

        @Nullable Point spawnLocation = event.getPlayer().getTargetBlockPosition(150);

        if (spawnLocation == null) return;

        float scatterRange = 1.25f;

        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            new Entity(EntityType.LIGHTNING_BOLT).setInstance(event.getInstance(), spawnLocation.add(
                    random.nextFloat(-scatterRange, scatterRange), 0f,
                    random.nextFloat(-scatterRange, scatterRange)
                ));
        }
    }
}
