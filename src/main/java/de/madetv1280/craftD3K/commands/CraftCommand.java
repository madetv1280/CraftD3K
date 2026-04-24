package de.madetv1280.craftD3K.commands;

import de.madetv1280.craftD3K.constants.Permission;
import de.madetv1280.craftD3K.constants.keys.Config;
import de.madetv1280.craftD3K.constants.keys.messages.Msg;
import de.madetv1280.craftD3K.managers.ConfigManager;
import de.madetv1280.craftD3K.managers.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static de.madetv1280.craftD3K.managers.MessageManager.getMessage;

public class CraftCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final CooldownManager cooldownManager;

    public CraftCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CooldownManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        // Get all config values
        boolean bypassCooldownAsOp = ConfigManager.getBoolean(Config.BYPASS_COOLDOWN_AS_OP, false);
        boolean showCooldownMsg = ConfigManager.getBoolean(Config.COOLDOWN_MSG, false);
        boolean showOpenMenuMsg = ConfigManager.getBoolean(Config.SHOW_OPEN_MENU_MSG, false);

        int cooldown = ConfigManager.getInt(Config.COOLDOWN, 0);
        long cooldownInMillis = TimeUnit.SECONDS.toMillis(cooldown);


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


        boolean hasBypassPermission = sender.hasPermission(Permission.BYPASS_COOLDOWN);
        boolean isOpWithBypassEnabled = sender.isOp()
                && bypassCooldownAsOp;

        UUID uuid = player.getUniqueId();

        // Check for cooldown
        if (!hasBypassPermission && !isOpWithBypassEnabled) {

            if (cooldownManager.isOnCooldown(uuid, cooldownInMillis)) {
                if (showCooldownMsg) {
                    long remaining = (cooldownManager.getRemainingSeconds(uuid, cooldownInMillis));
                    sender.sendMessage(getMessage(Msg.COOLDOWN).replace("%s", String.valueOf(remaining)));
                }
                return true;
            }
            cooldownManager.setCooldown(uuid);
        }

        // Check if msg is needed
        if (showOpenMenuMsg) {
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
