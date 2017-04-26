package me.mc_elmo.hub;

import me.mc_elmo.hub.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.logging.Logger;

/**
 * Created by Elom on 4/24/17.
 */
public class Hub extends JavaPlugin
{
    private Hub instance;
    private PlayerJoin playerJoin;
    private PluginManager pluginManager;
    private ScoreboardManager scoreboardManager;
    private Logger logger;

    @Override
    public void onEnable()
    {
        this.logger = Bukkit.getLogger();
        this.instance = this;
        this.playerJoin = new PlayerJoin(instance);
        this.pluginManager = getServer().getPluginManager();
        this.scoreboardManager = getServer().getScoreboardManager();
        registerEvents();
        registerListeners();
        registerCommands();
    }

    private void registerListeners()
    {
        pluginManager.registerEvents(playerJoin, instance);

    }


    private void registerEvents()
    {
    }

    private void registerCommands()
    {

    }

    public Logger getPluginLogger()
    {
        return logger;
    }

    @Override
    public void onDisable()
    {
        instance = null;
        pluginManager = null;
        scoreboardManager = null;
        logger = null;
    }
}
