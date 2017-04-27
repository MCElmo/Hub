package me.mc_elmo.hub.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.mc_elmo.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elom on 4/26/17.
 */
public class PluginMessage implements PluginMessageListener
{
    private Hub instance;
    private ByteArrayDataInput in;
    private ByteArrayDataOutput out;
    private String bungeeChannel;
    public PluginMessage(Hub instance)
    {
        this.instance = instance;

        this.bungeeChannel = "BungeeCord";
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, bungeeChannel);
        instance.getServer().getMessenger().registerIncomingPluginChannel(instance, bungeeChannel, this);
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message)
    {
        if(!(channel.equals(bungeeChannel)))
            return;

        in = ByteStreams.newDataInput(message);

    }

    public void connectPlayer(Player sender, String server)
    {
        out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        sender.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
    }

    public void connectTarget(Player sender, Player target,  String server)
    {
        out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(target.getDisplayName());
        out.writeUTF(server);
        sender.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
    }

    public String ip(Player target)
    {
        out.writeUTF("IP");
        target.sendPluginMessage(instance, bungeeChannel , out.toByteArray());
        String ip = in.readUTF();
        int port = in.readInt();
        return ip + ":" + port;
    }

    public int playerCount(Player sender, String server)
    {
       out.writeUTF("PlayerCount");
       out.writeUTF(server);
        sender.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
       in.readUTF(); //Name of server is returned back
       return in.readInt(); //playercount

    }

    public List<Player> playerList(Player sender, String server)
    {
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        sender.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
        in.readUTF(); //Name of server is returned back
        String[] playerListString = in.readUTF().split(",");
        List<Player> playerList= new ArrayList<Player>();
        for(String playerName : playerListString)
        {
            playerList.add(Bukkit.getPlayerExact(playerName));
        }
        return playerList;
    }

    public void message(Player sender, Player target,  String message)
    {
        out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(target.getDisplayName());
        out.writeUTF(message);
        sender.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
    }

    public String getServer(Player target)
    {
        out.writeUTF("GetServer");
        target.sendPluginMessage(instance, bungeeChannel, out.toByteArray());
        return in.readUTF();
    }
    public String getUUID(Player target)
    {
        out.writeUTF("UUIDOther");
        out.writeUTF(target.getDisplayName());
        in.readUTF();
        return in.readUTF();
    }


}
