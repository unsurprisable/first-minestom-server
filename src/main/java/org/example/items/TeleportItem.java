package org.example.items;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class TeleportItem extends CustomItem {
    TeleportItem() {
        super(
            ItemStack.builder(Material.DIAMOND_SHOVEL)
                .customName(Component.text("Aspect of the Void", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false))
        );
    }


    private final int teleportRange = 12;


    @Override
    public void rightClick(PlayerUseItemEvent event) {

        Player player = event.getPlayer();

        // if there is a targetable block in range, tp the player there.
        @Nullable Point blockPosition = player.getTargetBlockPosition(teleportRange);
        if (blockPosition != null) {
            Pos destination = Pos.fromPoint(blockPosition).withView(player.getPosition());
            player.teleport(destination);
            return;
        }

        Pos position = player.getPosition();
        Vec direction = position.direction();

        Vec relativeVec = direction.mul(teleportRange);
        Pos destination = position.add(relativeVec.x(), relativeVec.y(), relativeVec.z());

        player.teleport(destination);

        player.playSound(Sound.sound(SoundEvent.ENTITY_ENDERMAN_TELEPORT, Sound.Source.BLOCK, 1f, 1f));
    }
}
