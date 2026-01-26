package de.madetv1280.craftD3K.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.IllegalFormatException;

import static de.madetv1280.craftD3K.managers.MessageManager.getMessage;

public class PluginLogger {
    private final JavaPlugin plugin;
    private static PluginLogger instance;

    public PluginLogger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void init(JavaPlugin plugin) {
        instance = new PluginLogger(plugin);
    }

    public static PluginLogger log() {
        return instance;
    }


    public void info(String msg_key, Object... args) {
        plugin.getLogger().info(formatMessage(msg_key, args));
    }

    public void warn(String msg_key, Object... args) {
        plugin.getLogger().warning(formatMessage(msg_key, args));
    }

    public void error(String msg_key, Object... args) {
        plugin.getLogger().severe(formatMessage(msg_key, args));
    }

    public void debug(String msg_key, Object... args) {
        if (plugin.getConfig().getBoolean("debug", false)) {
            plugin.getLogger().info("[DEBUG] " + formatMessage(msg_key, args));
        }

    }

    // Get msg via key and fill in the args
    private String formatMessage(String key, Object... args) {
        String msg = getMessage(key);

        if (args != null && args.length > 0) {
            try {
                msg = String.format(msg, args);
            } catch (IllegalFormatException e) {
                msg = "Message format error for key '" + key + "': " + e.getMessage();
            }
        }
        return msg;
    }
}
