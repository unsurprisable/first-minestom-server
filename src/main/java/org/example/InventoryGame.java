package org.example;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;

import java.util.Random;

public class InventoryGame {

    private final Player player;
    private final Inventory gameInventory;
    private int currentSlot;
    private int score;

    private int obstacleRow;
    private final ItemStack[] obstacles = new ItemStack[9];

    private final ItemStack.Builder playerItemBuilder = ItemStack.builder(Material.PLAYER_HEAD).customName(Component.empty());
    private final ItemStack.Builder obstacleItemBuilder = ItemStack.builder(Material.ANVIL).customName(Component.empty());

    public InventoryGame(Player targetPlayer) {

        player = targetPlayer;
        gameInventory = new Inventory(InventoryType.CHEST_6_ROW, Component.text("Score: 0", NamedTextColor.RED).decorate(TextDecoration.BOLD));
        currentSlot = 40;
        score = 0;

        player.openInventory(gameInventory);

        for (int slot = 45; slot < 54; slot++) {
            gameInventory.setItemStack(slot, ItemStack.builder(Material.LIME_STAINED_GLASS_PANE)
                .customName(Component.empty())
                .build()
            );
        }
        gameInventory.setItemStack(currentSlot, playerItemBuilder.build());

        GenerateNewObstacles();

        gameInventory.addInventoryCondition((player, slot, clickType, inventoryConditionResult) -> {
            inventoryConditionResult.setCancel(true);

            if (slot == currentSlot) {
                SkipTurn();
                return;
            }

            if (slot < 45 || slot > 54) return;

            if (clickType == ClickType.LEFT_CLICK) {
                MoveLeft();
            } else if (clickType == ClickType.RIGHT_CLICK) {
                MoveRight();
            }
            MoveObstacles();

        });
    }

    private void GenerateNewObstacles() {
        Random random = new Random();
        int obstacleSlot = random.nextInt(0,9);
        obstacleRow = 0;

        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = i != obstacleSlot ? obstacleItemBuilder.build() : ItemStack.AIR;
        }

        DrawObstacles(obstacleRow);
    }

    private void ClearObstacles(int row) {
        for (int slot = row * 9; slot < row * 9 + 9; slot++) {
            if (gameInventory.getItemStack(slot).material().equals(obstacleItemBuilder.build().material())) {
                gameInventory.setItemStack(slot, ItemStack.AIR);
            }
        }
    }

    private void DrawObstacles(int row) {
        for (int i = 0; i < 9; i++) {
            if (obstacles[i] == ItemStack.AIR) continue;

            int slot = i + row * 9;

            if (slot == currentSlot) {
                LoseGame();
                return;
            }

            gameInventory.setItemStack(slot, obstacleItemBuilder.build());
        }
    }

    private void MoveObstacles() {
        ClearObstacles(obstacleRow);
        obstacleRow += 1;

        if (obstacleRow == 5) {
            GenerateNewObstacles();
            score++;
            gameInventory.setTitle(Component.text("Score: " + score, NamedTextColor.RED).decorate(TextDecoration.BOLD));
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_YES, Sound.Source.MASTER, 100f, 1.5f));
            player.playSound(Sound.sound(SoundEvent.ENTITY_EXPERIENCE_ORB_PICKUP, Sound.Source.MASTER, 100f, 2f));
        } else {
            DrawObstacles(obstacleRow);
        }
    }

    private void MovePlayerItem(int direction) {
        gameInventory.setItemStack(currentSlot, ItemStack.AIR);

        currentSlot += direction;
        currentSlot = currentSlot % 9 + 36;

        gameInventory.setItemStack(currentSlot, playerItemBuilder.build());

        player.playSound(Sound.sound(SoundEvent.ENTITY_CHICKEN_EGG, Sound.Source.MASTER, 100f, 1.25f));
    }

    private void MoveLeft() {
        MovePlayerItem(-1);
    }

    private void MoveRight() {
        MovePlayerItem(+1);
    }

    private void SkipTurn() {
        MoveObstacles();
        player.playSound(Sound.sound(SoundEvent.BLOCK_WOOD_BREAK, Sound.Source.MASTER, 100f, 1f));
    }

    private void LoseGame() {
        player.sendMessage("Oh no, you got hit :(((");
        player.playSound(Sound.sound(SoundEvent.ENTITY_GENERIC_EXPLODE, Sound.Source.MASTER, 100f, 1.5f));
        player.closeInventory();
    }

}
