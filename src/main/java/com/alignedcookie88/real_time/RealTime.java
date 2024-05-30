package com.alignedcookie88.real_time;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

public class RealTime implements ModInitializer {

    public static TimeManager manager;

    public static Logger LOGGER = LoggerFactory.getLogger("IRL Time");

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
        manager = new TimeManager();

        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if (server != null) {
                server.sendTimeUpdatePackets();
            } else {
                LOGGER.warn("No server is known to the mod.");
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if (world.isClient)
                return ActionResult.PASS;

            if (world.isDay())
                return ActionResult.PASS;

            Block[] blocks = new Block[] {
                    Blocks.BLACK_BED,
                    Blocks.BLUE_BED,
                    Blocks.BROWN_BED,
                    Blocks.CYAN_BED,
                    Blocks.GRAY_BED,
                    Blocks.GREEN_BED,
                    Blocks.LIGHT_BLUE_BED,
                    Blocks.LIGHT_GRAY_BED,
                    Blocks.LIME_BED,
                    Blocks.MAGENTA_BED,
                    Blocks.PINK_BED,
                    Blocks.PURPLE_BED,
                    Blocks.RED_BED,
                    Blocks.WHITE_BED,
                    Blocks.YELLOW_BED
            };

            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();

            if (Arrays.asList(blocks).contains(block)) {

                String[] messages = new String[] {
                        "Try sleeping in real life.",
                        "Sorry, you have insomnia.",
                        "Have you tried counting sheep?",
                        "I told you not to drink coke after 2 o' clock!",
                        "I've heard taking melatonin helps.",
                        "Maybe you should talk to your doctor.",
                        "Have you tried aromatherapy?"
                };

                player.sendMessage(Text.of(messages[new Random().nextInt(messages.length)]), true);
                return ActionResult.CONSUME;
            }

            return ActionResult.PASS;
        });

    }
}
