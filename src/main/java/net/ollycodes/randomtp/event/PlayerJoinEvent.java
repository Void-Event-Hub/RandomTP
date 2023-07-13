package net.ollycodes.randomtp.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ollycodes.randomtp.RandomTP;
import net.ollycodes.randomtp.config.ModConfig;
import net.ollycodes.randomtp.util.Teleport;

public class PlayerJoinEvent {
    public static String firstJoinTag = RandomTP.MOD_ID + ".first_join";

    public static void onSpawn(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        ServerPlayerEntity player = serverPlayNetworkHandler.player;
        if (player.getScoreboardTags().contains(firstJoinTag)) {
            RandomTP.LOGGER.info("Returning player " + player.getName().getString() + " joined");
            return;
        }
        player.addScoreboardTag(firstJoinTag);
        RandomTP.LOGGER.info("New player " + player.getName().getString() + " joined");
        if (ModConfig.teleportOnFirstJoin) {
            Teleport.randomTP(player, 0);
        }
    }
}
