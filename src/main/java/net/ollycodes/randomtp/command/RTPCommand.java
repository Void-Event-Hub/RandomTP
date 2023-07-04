package net.ollycodes.randomtp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ollycodes.randomtp.util.Teleport;

import java.util.Collection;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.EntityArgumentType.players;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RTPCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        if (!registrationEnvironment.dedicated) return;
        dispatcher.register(literal("rtp")
            .requires(source -> source.hasPermissionLevel(4))
            .then(argument("target", players())
            .executes(context -> {
                Collection<ServerPlayerEntity> targets = getPlayers(context, "target");
                for (ServerPlayerEntity target : targets) {
                    Teleport.randomTP(target, 0);
                }
                context.getSource().sendMessage(Text.literal("[RandomTP] Randomly teleported " + targets.size() + " players").formatted(Formatting.GREEN));
                return 1;
            }))
        );
    }
}
