package me.mc_elmo.hub;

import me.mc_elmo.hub.listeners.HubManagementListeners;
import me.mc_elmo.hub.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class Hub
        extends JavaPlugin {
    private Hub instance;
    private Connection connection;
    private PlayerJoin playerJoin;
    private PluginManager pluginManager;
    private ScoreboardManager scoreboardManager;
    private Logger logger;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.logger = Bukkit.getLogger();
        this.config = this.getConfig();
        this.config.options().copyDefaults(true);
        this.saveDefaultConfig();
        this.instance = this;
        this.sql();
        this.playerJoin = new PlayerJoin(this.instance);
        this.pluginManager = this.getServer().getPluginManager();
        this.scoreboardManager = this.getServer().getScoreboardManager();
        this.registerEvents();
        this.registerListeners();
        this.registerCommands();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                    for (World world : Bukkit.getServer().getWorlds()) {
                        world.setTime(0);
                        this.getLogger().info("Time change");
                    }
                }
                , 0, 100);
    }

    private void registerListeners() {
        this.pluginManager.registerEvents(this.playerJoin, this.instance);
        this.pluginManager.registerEvents(new HubManagementListeners(), this);
    }

    private void registerEvents() {
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void registerCommands() {
    }

    public Logger getPluginLogger() {
        return this.logger;
    }

    private void sql() {
        this.establishConnection();
    }

    private void establishConnection() {
        String hostname = this.config.getString("SQL.host", "localhost");
        String port = this.config.getString("SQL.port", "3306");
        String databaseName = this.config.getString("SQL.database", "default");
        String user = this.config.getString("SQL.user", "root");
        String password = this.config.getString("SQL.auth", "");
        String connectionStr = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(connectionStr, user, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        this.instance = null;
        this.pluginManager = null;
        this.scoreboardManager = null;
        this.logger = null;
    }
}