package net.ollycodes.randomtp;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.ollycodes.randomtp.command.*;
import net.ollycodes.randomtp.config.ModConfig;
import net.ollycodes.randomtp.event.PlayerJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomTP implements ModInitializer {
	public static final String MOD_ID = "randomtp";
    public static final Logger LOGGER = LoggerFactory.getLogger("RandomTP");

	@Override
	public void onInitialize() {
		ModConfig.registerConfig();
		CommandRegistrationCallback.EVENT.register(RTPCommand::register);
		CommandRegistrationCallback.EVENT.register(ResetJoinCommand::register);
		CommandRegistrationCallback.EVENT.register(ConfigCommand::register);
		if (ModConfig.debug) {
			LOGGER.info("Debug mode enabled - registering additional commands");
			CommandRegistrationCallback.EVENT.register(SpawnPlayersCommand::register);
			CommandRegistrationCallback.EVENT.register(GetYCommand::register);
		}
		ServerPlayConnectionEvents.JOIN.register(PlayerJoinEvent::onSpawn);
		LOGGER.info("Mod initialized");
	}
}