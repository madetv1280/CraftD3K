package de.madetv1280.craftD3K.managers;

import de.madetv1280.craftD3K.constants.keys.Config;
import de.madetv1280.craftD3K.constants.keys.messages.InternalMsg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static de.madetv1280.craftD3K.util.PluginLogger.log;

public class ConfigManager {
    private static JavaPlugin plugin;

    private static boolean pluginEnabled;
    private static boolean debugEnabled;
    private static boolean bStatsEnabled;

    public static void init(JavaPlugin mainPlugin) {
        plugin = mainPlugin;

        pluginEnabled = getBoolean(Config.PLUGIN_ENABLED, true);
        debugEnabled  = getBoolean(Config.DEBUG, false);
        bStatsEnabled = getBoolean(Config.BSTATS, true);
    }

    // Methods to get specific value for ifs
    public static boolean pluginDisabled() { return !pluginEnabled; }
    public static boolean debugEnabled()  { return debugEnabled; }
    public static boolean bStatsEnabled() { return bStatsEnabled; }


    public static boolean getBoolean(String path, boolean defaultValue) {
        Object value = plugin.getConfig().get(path);
        if (value instanceof Boolean b) {
            return b;
        } else {
            log().warn(InternalMsg.INCORRECT_DATATYPE, path, "boolean");
            return defaultValue;
        }
    }

    public static int getInt(String path, int defaultValue) {
        Object value = plugin.getConfig().get(path);
        if (value instanceof Number n) {
            return n.intValue();
        } else {
            log().warn(InternalMsg.INCORRECT_DATATYPE, path, "integer");
            return defaultValue;
        }
    }

    public static String getString(String path, String defaultValue) {
        Object value = plugin.getConfig().get(path);
        if (value instanceof String s) {
            return s;
        } else {
            log().warn(InternalMsg.INCORRECT_DATATYPE, path, "String");
            return defaultValue;
        }
    }


    public static FileConfiguration getConfig() {
        return plugin.getConfig();

    }

}
