package net.ollycodes.randomtp.config;

import com.mojang.datafixers.util.Pair;
import net.ollycodes.randomtp.RandomTP;

public class ModConfig {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configProvider;

    public static int centerX;
    public static int centerZ;
    public static int spawnRadius;
    public static int maxTries;
    public static int minPlayerDist;
    public static boolean ignoreCreative;
    public static boolean teleportOnFirstJoin;
    public static boolean debug;

    public static void registerConfig() {
        configProvider = new ModConfigProvider();
        createConfig();
        CONFIG = SimpleConfig.of("random-tp").provider(configProvider).request();
        assignConfig();
        RandomTP.LOGGER.info("Config loaded");
    }

    public static void reloadConfig() {
        CONFIG = SimpleConfig.of("random-tp").provider(configProvider).request();
        assignConfig();
        RandomTP.LOGGER.info("Config reloaded");
    }

    private static void createConfig() {
        configProvider.addKeyValuePair(new Pair<>("centerX", 0), "The x coordinate of the center of the spawn area");
        configProvider.addKeyValuePair(new Pair<>("centerZ", 0), "The z coordinate of the center of the spawn area");
        configProvider.addKeyValuePair(new Pair<>("spawnRadius", 1000), "The radius of the spawn area");
        configProvider.addKeyValuePair(new Pair<>("maxTries", 10), "The maximum number of tries to find a suitable location");
        configProvider.addKeyValuePair(new Pair<>("minPlayerDist", 50), "The minimum distance between players");
        configProvider.addKeyValuePair(new Pair<>("ignoreCreative", true), "Whether to ignore players in creative mode when checking for nearby players");
        configProvider.addKeyValuePair(new Pair<>("teleportOnFirstJoin", true), "Whether to teleport players to a random location when they first join the server");
        configProvider.addKeyValuePair(new Pair<>("debug", false), "Enables debug mode (requires the carpet mod)");
    }

    private static void assignConfig() {
        centerX = CONFIG.getOrDefault("centerX", 0);
        centerZ = CONFIG.getOrDefault("centerZ", 0);
        spawnRadius = CONFIG.getOrDefault("spawnRadius", 1000);
        maxTries = CONFIG.getOrDefault("maxTries", 10);
        minPlayerDist = CONFIG.getOrDefault("minPlayerDist", 50);
        ignoreCreative = CONFIG.getOrDefault("ignoreCreative", true);
        teleportOnFirstJoin = CONFIG.getOrDefault("teleportOnFirstJoin", true);
        debug = CONFIG.getOrDefault("debug", false);
    }
}
