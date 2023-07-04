package net.ollycodes.randomtp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.ollycodes.randomtp.RandomTP;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.command.argument.Vec3ArgumentType.getVec3;
import static net.minecraft.command.argument.Vec3ArgumentType.vec3;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SpawnPlayersCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        if (!registrationEnvironment.dedicated) return;
        dispatcher.register(literal("rtp").then(literal("bot").then(literal("spawn")
            .then(argument("count", integer())
            .then(argument("center", vec3())
            .executes(context -> {
                Vec3d pos = getVec3(context, "center");
                String coords = pos.getX() + " " + pos.getY() + " " + pos.getZ();
                RandomTP.LOGGER.info("Spawning " + getInteger(context, "count") + " players at " + coords);
                for (int i = 0; i < getInteger(context, "count"); i++) {
                    executeCommand(context, "player Player" + i + " spawn at " + coords + " facing 0 0");
                    executeCommand(context, "tag Player" + i + " add bot");
                }
                return 1;
            })))
        )));

        dispatcher.register(literal("rtp").then(literal("bot").then(literal("kill")
            .then(argument("count", integer())
            .executes(context -> {
                for (int i = 0; i < getInteger(context, "count"); i++) {
                    executeCommand(context, "player Player" + i + " kill");
                }
                return 1;
            }))
        )));
    }

    private static void executeCommand(CommandContext<ServerCommandSource> context, String command) {
        context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource(), command);
    }
}
