package de.madetv1280.craftD3K.commands;

import de.madetv1280.craftD3K.constants.keys.Config;
import de.madetv1280.craftD3K.constants.keys.messages.Msg;
import de.madetv1280.craftD3K.constants.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static de.madetv1280.craftD3K.managers.MessageManager.getMessage;

public class CraftCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public CraftCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        // Check if sender is a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(getMessage(Msg.ONLY_PLAYER));
            return true;
        }

        // Check for permission
        if (!sender.hasPermission(Permission.USE_CRAFT)) {
            sender.sendMessage(getMessage(Msg.NO_PERMISSION));
            return true;
        }


        // Cooldown logic
        if (!sender.hasPermission(Permission.BYPASS_COOLDOWN)) {
            // Cooldown logic
        }



        // Check if opening-msg is needed
        if (plugin.getConfig().getBoolean(Config.SHOW_OPEN_MENU_MSG)) {
            sender.sendMessage(getMessage(Msg.OPENING_MENU));
        }

        openCraftingTable(player);

        return true;
    }

    private void openCraftingTable(Player player) {
        Inventory inventory = Bukkit.createInventory(player, InventoryType.WORKBENCH);
        player.openInventory(inventory);
    }

}
