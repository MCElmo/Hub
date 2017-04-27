package me.mc_elmo.hub;

import me.mc_elmo.hub.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;
import redis.clients.jedis.Jedis;

import java.util.logging.Logger;

/**
 * Created by Elom on 4/24/17.
 */
public class Hub extends JavaPlugin
{
    private Hub instance;

    private Jedis jedis;

    private PlayerJoin playerJoin;

    private PluginManager pluginManager;
    private ScoreboardManager scoreboardManager;
    private Logger logger;
    private FileConfiguration config;
    @Override
    public void onEnable()
    {
        this.logger = Bukkit.getLogger();

            this.config = getConfig();
            config.options().copyDefaults(true);
            saveDefaultConfig();

        this.instance = this;
        this.playerJoin = new PlayerJoin(instance);
        this.pluginManager = getServer().getPluginManager();
        this.scoreboardManager = getServer().getScoreboardManager();
        registerEvents();
        registerListeners();
        registerCommands();
        jedis();
    }

    private void jedis()
    {
        jedis = new Jedis(config.getString("Jedis.host" , "localhsot"), config.getInt("Jedis.port", 6379), config.getInt("Jedis.timeout" , 5000));

        if(config.getString("Jedis.password") != null)
            jedis.auth(config.getString("Jedis.password"));


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

    public Jedis getJedis()
    {
        return jedis;
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
        jedis.close();
    }
}
