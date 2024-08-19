package org.example.block_handlers;

import net.kyori.adventure.sound.Sound;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

public class ChestHandler implements BlockHandler {

    private final Inventory chestInventory = new Inventory(InventoryType.CHEST_3_ROW, "Chest");

    @Override
    public boolean onInteract(@NotNull BlockHandler.Interaction interaction) {
        interaction.getInstance().playSound(Sound.sound(SoundEvent.BLOCK_CHEST_OPEN, Sound.Source.BLOCK, 5f, 1f), interaction.getBlockPosition());
        interaction.getPlayer().openInventory(chestInventory);

        interaction.getInstance().sendGroupedPacket(new BlockActionPacket(
            interaction.getBlockPosition(),
            (byte) 1,
            (byte) 1,
            Block.CHEST
        ));

        MinecraftServer.getSchedulerManager().submitTask(() -> {
            if (interaction.getPlayer().getInstance() != interaction.getInstance() || interaction.getPlayer().getOpenInventory() != chestInventory) {
                interaction.getInstance().playSound(Sound.sound(SoundEvent.BLOCK_CHEST_CLOSE, Sound.Source.BLOCK, 5f, 1f), interaction.getBlockPosition());
                interaction.getInstance().sendGroupedPacket(new BlockActionPacket(
                    interaction.getBlockPosition(),
                    (byte) 1,
                    (byte) 0,
                    Block.CHEST
                ));
                return TaskSchedule.stop();
            }
            return TaskSchedule.tick(2);
        });

        return false;
    }

    @Override
    public void onDestroy(@NotNull BlockHandler.Destroy destroy) {
        for (Player player : chestInventory.getViewers()) {
            player.closeInventory();
        }
    }

    @Override
    public @NotNull NamespaceID getNamespaceId() {
        return NamespaceID.from("first-minestom-server:chest");
    }
}
