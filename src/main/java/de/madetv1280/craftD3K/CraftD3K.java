package de.madetv1280.craftD3K;

import de.madetv1280.craftD3K.commands.CraftCommand;
import de.madetv1280.craftD3K.commands.CraftReloadCommand;
import de.madetv1280.craftD3K.constants.Command;
import de.madetv1280.craftD3K.constants.Integration;
import de.madetv1280.craftD3K.constants.keys.messages.InternalMsg;
import de.madetv1280.craftD3K.managers.ConfigManager;
import de.madetv1280.craftD3K.managers.MessageManager;
import de.madetv1280.craftD3K.util.Metrics;
import de.madetv1280.craftD3K.util.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import static de.madetv1280.craftD3K.managers.ConfigManager.*;
import static de.madetv1280.craftD3K.util.PluginLogger.log;
import static de.madetv1280.craftD3K.managers.UpdateChecker.checkForUpdates;

public final class CraftD3K extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save config
        saveDefaultConfig();

        ConfigManager.init(this);
        PluginLogger.init(this);
        MessageManager.init(this);

        // Early exit
        if (!pluginEnabled()) onDisable();

        registerCommands();

        if (debugEnabled()) log().debug(InternalMsg.DEBUG_ENABLED);

        // Enable bStats
        if (bStatsEnabled()) initMetrics();

        checkForUpdates(this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log().info(InternalMsg.DISABLE_PLUGIN);
        getServer().getPluginManager().disablePlugin(this);
    }


    private void registerCommands() {
        CraftCommand craftCommand = new CraftCommand(this);

        this.getCommand(Command.CRAFT).setExecutor(craftCommand);
        this.getCommand(Command.C).setExecutor(craftCommand);

        this.getCommand(Command.CRAFTRELOAD).setExecutor(new CraftReloadCommand());
    }

    private void initMetrics() {
        // init Metrics
        Metrics metrics = new Metrics(this, Integration.METRICS_PLUGIN_ID);

        log().info(InternalMsg.USING_BSTATS);
    }

}
