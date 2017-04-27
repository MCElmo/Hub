package me.mc_elmo.hub.listeners;

import me.mc_elmo.hub.Hub;
import me.mc_elmo.hub.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Elom on 4/25/17.
 */
public class PlayerJoin implements Listener
{
    private Hub instance;
    private FileConfiguration config;
    public PlayerJoin(Hub instance)
    {
        this.instance = instance;
        this.config = instance.getConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        //Send player join message in form of title
        Player player = e.getPlayer();
        try
        {
            ChatUtil.sendTitle(player, config.getString("Join.Title"), config.getString("Join.subTitle"),
                    config.getInt("Join.fadeIn"), config.getInt("Join.stay"), config.getInt("Join.fadeOut"));
        }catch (NullPointerException ex)
        {
            instance.getLogger().warning("Unable to get Join Title info, message did not send.");
        }


    }
}
