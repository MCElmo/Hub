package me.mc_elmo.hub.util;

import org.bukkit.ChatColor;

/**
 * Created by Elom on 4/24/17.
 */
public class ChatUtil
{
    public static String format(String msg)
    {
        return ChatColor.translateAlternateColorCodes('&',msg);
    }
}
