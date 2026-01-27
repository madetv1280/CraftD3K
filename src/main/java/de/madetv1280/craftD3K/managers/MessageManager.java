package de.madetv1280.craftD3K.managers;

import de.madetv1280.craftD3K.constants.FileName;
import de.madetv1280.craftD3K.constants.keys.Config;
import de.madetv1280.craftD3K.constants.keys.messages.InternalMsg;
import de.madetv1280.craftD3K.constants.keys.messages.Msg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessageManager {

    private static FileConfiguration messages;
    private static File file;

    public static void init(JavaPlugin plugin) {

        file = new File(plugin.getDataFolder(), FileName.MESSAGES_YML);

        if (!file.exists()) {
            plugin.saveResource(FileName.MESSAGES_YML, false);
        }

        messages = YamlConfiguration.loadConfiguration(file);

    }

    public static String getMessage(String key) {
        if (messages.contains(key)) {
            String prefix = messages.getString(Msg.PREFIX, "");
            String msg = messages.getString(key);

            return (prefix + msg).replace("&", "§").trim();
        }
        return getMessage(InternalMsg.MESSAGE_NOT_FOUND).replace("{key}", key);

    }

    public static String getMessage(String key, String placeholder, String value) {
        return getMessage(key).replace("{" + placeholder + "}", value);
    }
}
