package net.ollycodes.randomtp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ollycodes.randomtp.config.ModConfig;

import static net.minecraft.server.command.CommandManager.literal;

public class ConfigCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("rtp").then(literal("reload")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(context -> {
                    ModConfig.reloadConfig();
                    context.getSource().sendMessage(Text.literal("[RandomTP] Config Reloaded").formatted(Formatting.GREEN));
                    return 1;
                })
        ));

        dispatcher.register(literal("rtp").then(literal("config")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(context -> {
                    context.getSource().sendMessage(Text.literal("[RandomTP] Config").formatted(Formatting.YELLOW));
                    context.getSource().sendMessage(Text.literal("centerX: " + ModConfig.centerX).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("centerZ: " + ModConfig.centerZ).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("spawnRadius: " + ModConfig.spawnRadius).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("maxTries: " + ModConfig.maxTries).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("minPlayerDist: " + ModConfig.minPlayerDist).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("ignoreCreative: " + ModConfig.ignoreCreative).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("debug: " + ModConfig.debug).formatted(Formatting.GOLD));
                    context.getSource().sendMessage(Text.literal("ignoreCreative: " + ModConfig.ignoreCreative).formatted(Formatting.GOLD));
                    return 1;
                })
        ));
    }
}
