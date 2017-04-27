package me.mc_elmo.hub.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Elom on 4/24/17.
 */
public class ChatUtil
{
    //Translate '&' to Chat Color
    public static String format(String msg)
    {
        return ChatColor.translateAlternateColorCodes('&',msg);
    }

    //Format the title message
    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(format(title), format(subTitle), fadeIn, stay, fadeOut);
    }
}
