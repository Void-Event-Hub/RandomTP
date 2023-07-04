package net.ollycodes.randomtp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ollycodes.randomtp.event.PlayerJoinEvent;

import java.util.Collection;
import java.util.Objects;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.EntityArgumentType.players;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ResetJoinCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("rtp").then(literal("resetjoin")
            .requires(source -> source.hasPermissionLevel(4))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (Objects.isNull(player)) {
                    context.getSource().sendMessage(
                            Text.literal("This command can only be run by a player.").formatted(Formatting.RED)
                    );
                    return 1;
                }
                player.getCommandTags().remove(PlayerJoinEvent.firstJoinTag);
                context.getSource().sendMessage(Text.literal("[RandomTP] First join tag removed").formatted(Formatting.GREEN));
                return 1;
            })
        ));

        dispatcher.register(literal("rtp").then(literal("resetjoin")
            .requires(source -> source.hasPermissionLevel(4))
            .then(argument("target", players())
            .executes(context -> {
                Collection<ServerPlayerEntity> targets = getPlayers(context, "target");
                for (ServerPlayerEntity target : targets) {
                    target.getCommandTags().remove(PlayerJoinEvent.firstJoinTag);
                }
                context.getSource().sendMessage(Text.literal("[RandomTP] Remove first join tag from " + targets.size() + " players").formatted(Formatting.GREEN));
                return 1;
            }))
        ));
    }
}
