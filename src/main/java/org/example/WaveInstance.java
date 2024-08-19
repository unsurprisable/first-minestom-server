package org.example;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;

import java.util.HashMap;
import java.util.UUID;

// well this did not work out how i expected it to...

public class WaveInstance extends InstanceContainer {
    public WaveInstance() {
        super(UUID.randomUUID(), DimensionType.OVERWORLD);

        setGenerator(unit -> unit.modifier().fillHeight(0, 39, Block.BARRIER));
        setChunkSupplier(LightingChunk::new);
        enableAutoChunkLoad(true);
        setTimeRate(0);
        setTime(6000);
    }


    final double bottom = 40;
    final double height = 4;
    final double period = 6.4;

    final double speed = 3;
    double lastTime = -1;
    double deltaTime = 0;


    private double WaveFunction(double x) {
        x += deltaTime;
        return -height/2 * Math.cos(Math.TAU * x / period) + bottom + height/2;
    }

    @Override
    public void tick(long time) {
        super.tick(time);

        if (lastTime != -1)
            deltaTime += (time - lastTime)/1000d * speed;
        lastTime = time;

        for (int x = 0; x < 32; x++) {
            for (int z = 0; z < 32; z++) {
                PlaceWaveBlock(new Pos(x, WaveFunction(x), z));
            }
        }
    }

    private void PlaceWaveBlock(Pos position) {
        double variance = position.y() - (int) position.y();
        int level = 0;
        if (variance < 1/8d) {
            level = 1;
        } else if (variance < 2/8d) {
            level = 2;
        } else if (variance < 3/8d) {
            level = 3;
        } else if (variance < 4/8d) {
            level = 4;
        } else if (variance < 5/8d) {
            level = 5;
        } else if (variance < 6/8d) {
            level = 6;
        } else if (variance < 7/8d) {
            level = 7;
        } else if (variance < 1d) {
            level = 8;
        }

        HashMap<String, String> properties = new HashMap<>();
        properties.put("layers", String.valueOf(level));

        Block topBlock = Block.SNOW.withProperties(properties);

        setBlock(position, topBlock);
        for (int y = (int) position.y() - 1; y >= bottom; y--) {
            setBlock(position.withY(y), Block.SNOW_BLOCK);
        }
        for (int y = (int) position.y() + 1; y < bottom + height; y++) {
            setBlock(position.withY(y), Block.AIR);
        }
    }
}
