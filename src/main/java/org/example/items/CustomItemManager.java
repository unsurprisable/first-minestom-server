package org.example.items;

import org.jetbrains.annotations.NotNull;

public class CustomItemManager {

    public static enum CustomItemMaterial {
        simple_gun,
        cool_firework,
        lightning_wand,
        aspect_of_the_void,
    }

    @NotNull
    public static CustomItem getCustomItemFromMaterial(CustomItemMaterial material) {
        return switch (material) {
            case simple_gun -> new SimpleGunItem();
            case cool_firework -> new CoolFireworkItem();
            case lightning_wand -> new LightningWandItem();
            case aspect_of_the_void -> new TeleportItem();
        };
    }
}
