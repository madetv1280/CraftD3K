package de.madetv1280.craftD3K.util;

import de.madetv1280.craftD3K.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.IllegalFormatException;

import static de.madetv1280.craftD3K.managers.MessageManager.getMessage;
import static de.madetv1280.craftD3K.managers.ConfigManager.*;

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
        if (debugEnabled()) {
            plugin.getLogger().info("[DEBUG] " + formatMessage(msg_key, args));
        }
    }

    // Get msg via key and fill in the args
    public String formatMessage(String key, Object... arguments) {
        String message = getMessage(key);

        if (arguments != null && arguments.length > 0) {
            try {
                message = String.format(message, arguments);
            } catch (IllegalFormatException e) {
                message = "Message format error for key '" + key + "': " + e.getMessage();
            }
        }
        return message;
    }
}
