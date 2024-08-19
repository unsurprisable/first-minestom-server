package org.example;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.CommandManager;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.monitoring.TickMonitor;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.MathUtils;
import org.example.event_handlers.BlockPlayerInteractionHandler;
import org.example.event_handlers.CustomItemUseHandler;
import org.example.event_handlers.PlayerConfigurationHandler;
import org.example.commands.*;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        MinecraftServer minecraftServer = MinecraftServer.init();

        // Initialize Instance and connect Player
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        InstanceContainer lobby = new WaveInstance();
        instanceManager.registerInstance(lobby);

//        InstanceContainer lobby = instanceManager.createInstanceContainer();
//        lobby.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.MOSS_BLOCK));
//        lobby.setChunkSupplier(LightingChunk::new);
//        lobby.enableAutoChunkLoad(true);
//        lobby.setTimeRate(0);



        // Register Commands
        {
            CommandManager manager = MinecraftServer.getCommandManager();
            manager.register(new GamemodeCommand());
            manager.register(new FlyCommand());
            manager.register(new TeleportCommand());
            manager.register(new SpawnBoatCommand());
            manager.register(new ClearEntitiesCommand());
            manager.register(new InventoryGameCommand());
            manager.register(new GiveCommand());
            manager.register(new GunCommand());
            manager.register(new CustomItemCommand());
            manager.register(new SetblockCommand());
        }

        // Setup Events
        {
            GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
            handler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
                PlayerConfigurationHandler.onPlayerConfiguration(event, lobby);
            });

            BlockPlayerInteractionHandler.hook(handler);
            CustomItemUseHandler.hook(handler);

            // Monitoring
            AtomicReference<TickMonitor> lastTick = new AtomicReference<>();
            handler.addListener(ServerTickMonitorEvent.class, event -> lastTick.set(event.getTickMonitor()));

            // Header/footer
            MinecraftServer.getSchedulerManager().scheduleTask(() -> {
                Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();
                if (players.isEmpty()) return;

                final Runtime runtime = Runtime.getRuntime();
                final TickMonitor tickMonitor = lastTick.get();
                final long ramUsage = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;

                final Component header = Component.empty()
                    .append(Component.text("RAM USAGE: ", NamedTextColor.GREEN)
                        .append(Component.text( ramUsage + " MB", NamedTextColor.DARK_GREEN))
                    .append(Component.text("  |  MSPT: ", NamedTextColor.GREEN))
                        .append(Component.text(MathUtils.round(tickMonitor.getTickTime(), 2) + "ms", NamedTextColor.DARK_GREEN)));

                Audiences.players().sendActionBar(header);

            }, TaskSchedule.tick(10), TaskSchedule.tick(10));
        }


        // Start Server
        MojangAuth.init();
        minecraftServer.start("0.0.0.0", 25565);

        System.out.println("\nServer started successfully!");

    }
}