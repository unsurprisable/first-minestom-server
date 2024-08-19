package org.example.event_handlers;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;

public class PlayerConfigurationHandler {

    public static void onPlayerConfiguration(AsyncPlayerConfigurationEvent event, InstanceContainer instanceContainer)
    {
        final Player player = event.getPlayer();

        event.setSpawningInstance(instanceContainer);
        player.setRespawnPoint(new Pos(0, 40, 0));

        player.setGameMode(GameMode.CREATIVE);
        player.setPermissionLevel(4);
    }
}
