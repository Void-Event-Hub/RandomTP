package net.ollycodes.randomtp.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.ollycodes.randomtp.RandomTP;
import net.ollycodes.randomtp.config.ModConfig;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Teleport {
    public static Integer getYCoord(ServerWorld world, int x, int z) {
        ArrayList<String> preventSpawn = new ArrayList<>();
        preventSpawn.add("block.minecraft.water");
        preventSpawn.add("block.minecraft.lava");
        preventSpawn.add("block.minecraft.bedrock");
        preventSpawn.add("block.minecraft.barrier");
        preventSpawn.add("block.minecraft.structure_void");

        ArrayList<String> continueCheck = new ArrayList<>();
        continueCheck.add("block.minecraft.air");
        continueCheck.add("block.minecraft.cave_air");
        continueCheck.add("block.minecraft.void_air");

        for (int y = 320; y > -64; y--) {
            BlockPos pos = BlockPos.fromLong(BlockPos.asLong(x, y, z));
            String blockName = world.getBlockState(pos).getBlock().getTranslationKey();
            if (preventSpawn.contains(blockName)) return null;
            if (!continueCheck.contains(blockName)) {
                RandomTP.LOGGER.info("Spawnable Block: " + blockName);
                return y + 1;
            }
        }
        return null;
    }

    public static boolean checkPosition(ServerWorld world, int x, int y, int z) {
        PlayerEntity closestPlayer = world.getClosestPlayer(x, y, z, ModConfig.spawnRadius, ModConfig.ignoreCreative);
        if (closestPlayer == null) {
            RandomTP.LOGGER.info("No players found near (" + x + ", " + y + ", " + z + ")");
            return true;
        }
        double distance = sqrt(
                pow(x - closestPlayer.getX(), 2)
                + pow(z - closestPlayer.getZ(), 2)
        );
        RandomTP.LOGGER.info("Closest player: " + closestPlayer.getName().toString() + " distance: " + distance);
        return !(distance < ModConfig.minPlayerDist);
    }

    public static void randomTP(ServerPlayerEntity target, int tries) {
        String name = target.getName().toString();
        if (tries > ModConfig.maxTries) {
            RandomTP.LOGGER.warn("RandomTP failed to find a suitable location for " + name + " after " + tries + " tries.");
            return;
        }
        int newX = ModConfig.centerX + (int) (Math.random() * ModConfig.spawnRadius * 2) - ModConfig.spawnRadius;
        int newZ = ModConfig.centerZ + (int) (Math.random() * ModConfig.spawnRadius * 2) - ModConfig.spawnRadius;
        Integer newY = getYCoord(target.getWorld(), newX, newZ);
        if (newY == null) {
            randomTP(target, tries + 1);
            return;
        }
        if (!checkPosition(target.getWorld(), newX, newY, newZ)) {
            randomTP(target, tries + 1);
            return;
        }
        RandomTP.LOGGER.info("RandomTP teleported: " + name + ", center: (" + ModConfig.centerX + ", " + ModConfig.centerZ + "), radius: " + ModConfig.spawnRadius + ", tries: " + tries + "/" + ModConfig.maxTries + ", minDist: " + ModConfig.minPlayerDist);
        target.teleport(newX, newY, newZ);
    }
}
