package org.example.entities;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.utils.time.TimeUnit;

import java.util.List;

public class EvilVillagerEntity extends EntityCreature {
    public EvilVillagerEntity() {
        super(EntityType.VILLAGER);

        this.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.265);

        addAIGroup(
            List.of(
                new MeleeAttackGoal(this, 1.5, 20, TimeUnit.SERVER_TICK),
                new RandomStrollGoal(this, 8)
            ),
            List.of(
                new ClosestEntityTarget(this, 10, entity -> entity instanceof Player)
            )
        );
    }
}
