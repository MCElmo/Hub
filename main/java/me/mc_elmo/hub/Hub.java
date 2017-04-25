package me.mc_elmo.hub;

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
    private PluginManager pluginManager;
    private ScoreboardManager scoreboardManager;
    private Logger logger;

    @Override
    public void onEnable()
    {
        this.logger = Bukkit.getLogger();
        this.instance = this;
        this.pluginManager = getServer().getPluginManager();
        this.scoreboardManager = getServer().getScoreboardManager();
        registerEvents();
        registerListeners();
        registerCommands();
    }

    private void registerListeners()
    {

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
