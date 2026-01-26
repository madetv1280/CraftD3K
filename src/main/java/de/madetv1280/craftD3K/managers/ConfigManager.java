package de.madetv1280.craftD3K.managers;

import de.madetv1280.craftD3K.constants.keys.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private static JavaPlugin plugin;

    private static boolean pluginEnabled;
    private static boolean debugEnabled;
    private static boolean bStatsEnabled;

    public static void init(JavaPlugin mainPlugin) {
        plugin = mainPlugin;
        pluginEnabled = plugin.getConfig().getBoolean(Config.PLUGIN_ENABLED);
        debugEnabled  = plugin.getConfig().getBoolean(Config.DEBUG);
        bStatsEnabled = plugin.getConfig().getBoolean(Config.BSTATS);
    }
    public static boolean pluginEnabled() { return pluginEnabled; }
    public static boolean debugEnabled()  { return debugEnabled; }
    public static boolean bStatsEnabled() { return bStatsEnabled; }


}
