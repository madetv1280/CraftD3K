package de.madetv1280.craftD3K.commands;

import de.madetv1280.craftD3K.constants.keys.messages.Msg;
import de.madetv1280.craftD3K.constants.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static de.madetv1280.craftD3K.managers.MessageManager.getMessage;
import static de.madetv1280.craftD3K.constants.PluginInfo.*;

public class CraftReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        // Check for permission
        if (!sender.hasPermission(Permission.USE_RELOAD)) {
            sender.sendMessage(getMessage(Msg.NO_PERMISSION));
            return true;
        }
        Plugin plugin = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);

        if (plugin == null) {
            return false;
        }

        // Reload plugin
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(plugin);

        plugin.reloadConfig();

        sender.sendMessage(getMessage(Msg.PLUGIN_RELODED));
        return true;
    }
}
