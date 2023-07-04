package net.ollycodes.randomtp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ollycodes.randomtp.util.Teleport;

import static net.minecraft.server.command.CommandManager.literal;

public class GetYCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("rtp").then(literal("gety")
            .requires(source -> source.hasPermissionLevel(4))
            .executes(context -> {
                if (context.getSource().getPlayer() == null) {
                    context.getSource().sendMessage(
                            Text.literal("This command can only be run by a player.").formatted(Formatting.RED)
                    );
                    return 1;
                }
                int playerX = (int) context.getSource().getPlayer().getX();
                int playerZ = (int) context.getSource().getPlayer().getZ();
                Integer y = Teleport.getYCoord(context.getSource().getWorld(), playerX, playerZ);
                if (y == null) {
                    context.getSource().sendMessage(Text.literal("No suitable location found.").formatted(Formatting.RED));
                    return 1;
                }
                context.getSource().sendMessage(Text.literal("Y: " + y).formatted(Formatting.GREEN));
                return 1;
            })
        ));
    }
}
