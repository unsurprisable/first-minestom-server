package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.notifications.Notification;
import net.minestom.server.advancements.notifications.NotificationCenter;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.BoatMeta;
import net.minestom.server.entity.metadata.villager.VillagerMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.timer.TaskSchedule;
import org.example.entities.EvilVillagerEntity;

public class SpawnBoatCommand extends Command {
    public SpawnBoatCommand() {
        super("boat");

        setDefaultExecutor((sender, context) -> {

            if (!(sender instanceof Player player)) return;

            Notification boatNotification = new Notification(
                Component.text("What a friendly boat :D", NamedTextColor.GREEN),
                FrameType.CHALLENGE,
                ItemStack.of(Material.OAK_BOAT)
            );

            NotificationCenter.send(boatNotification, player);

            Entity boatEntity = new Entity(EntityType.CHEST_BOAT);

            BoatMeta boatMeta = (BoatMeta) boatEntity.getEntityMeta();
            boatMeta.setNotifyAboutChanges(false);
            boatMeta.setCustomNameVisible(true);
            boatMeta.setCustomName(Component.text("Friendly boat UWU", NamedTextColor.AQUA));
            boatMeta.setNotifyAboutChanges(true);

            Instance instance = player.getInstance();
            Pos spawnPosition = player.getPosition()
                .add(player.getPosition().direction().mul(3f))
                .withLookAt(player.getPosition().add(0f, 1f, 0f))
                .withY(player.getPosition().y());

            boatEntity.setInstance(instance, spawnPosition);

            int swapDelay = 130; // in ticks

            MinecraftServer.getSchedulerManager().scheduleTask(() -> {

                Notification badNotification = new Notification(
                    Component.text("Uh oh...", NamedTextColor.RED),
                    FrameType.TASK,
                    ItemStack.of(Material.BARRIER)
                );

                NotificationCenter.send(badNotification, player);

                boatEntity.remove();

                EvilVillagerEntity evilVillager = new EvilVillagerEntity();

                VillagerMeta villagerMeta = (VillagerMeta) evilVillager.getEntityMeta();
                villagerMeta.setNotifyAboutChanges(false);
                villagerMeta.setFlyingWithElytra(true);
                villagerMeta.setOnFire(true);
                villagerMeta.setCustomNameVisible(true);
                villagerMeta.setCustomName(Component.text("BOAT HATER >:)", NamedTextColor.RED));
                villagerMeta.setNotifyAboutChanges(true);

                evilVillager.setInstance(instance, spawnPosition);

            }, TaskSchedule.tick(swapDelay), TaskSchedule.stop());

        });
    }


}
