package me.mc_elmo.hub.listeners;

import me.mc_elmo.hub.Hub;
import me.mc_elmo.hub.tasks.LoadDataTask;
import me.mc_elmo.hub.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoin
        implements Listener {
    private Hub instance;
    private FileConfiguration config;

    public PlayerJoin(Hub instance) {
        this.instance = instance;
        this.config = instance.getConfig();
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        LoadDataTask loadDataTask = new LoadDataTask(p, this.instance);
        loadDataTask.runTaskAsynchronously(this.instance);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        LoadDataTask loadDataTask = new LoadDataTask(p, this.instance);
        loadDataTask.runTaskAsynchronously(this.instance);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        this.config = this.instance.getConfig();
        Player player = e.getPlayer();
        try {
            ChatUtil.sendTitle(player, this.config.getString("Join.Title"), this.config.getString("Join.subTitle"), this.config.getInt("Join.fadeIn"), this.config.getInt("Join.stay"), this.config.getInt("Join.fadeOut"));
        }
        catch (NullPointerException ex) {
            this.instance.getLogger().warning("Unable to get Join Title info, message did not send.");
        }
        e.setJoinMessage("");
    }
}