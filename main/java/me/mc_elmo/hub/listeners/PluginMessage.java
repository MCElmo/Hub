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

public class PluginMessage
        implements PluginMessageListener {
    private Hub instance;
    private ByteArrayDataInput in;
    private ByteArrayDataOutput out;
    private String bungeeChannel;

    public PluginMessage(Hub instance) {
        this.instance = instance;
        this.bungeeChannel = "BungeeCord";
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, this.bungeeChannel);
        instance.getServer().getMessenger().registerIncomingPluginChannel(instance, this.bungeeChannel, this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals(this.bungeeChannel)) {
            return;
        }
        this.in = ByteStreams.newDataInput(message);
    }

    public void connectPlayer(Player sender, String server) {
        this.out = ByteStreams.newDataOutput();
        this.out.writeUTF("Connect");
        this.out.writeUTF(server);
        sender.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
    }

    public void connectTarget(Player sender, Player target, String server) {
        this.out = ByteStreams.newDataOutput();
        this.out.writeUTF("ConnectOther");
        this.out.writeUTF(target.getDisplayName());
        this.out.writeUTF(server);
        sender.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
    }

    public String ip(Player target) {
        this.out.writeUTF("IP");
        target.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
        String ip = this.in.readUTF();
        int port = this.in.readInt();
        return ip + ":" + port;
    }

    public int playerCount(Player sender, String server) {
        this.out.writeUTF("PlayerCount");
        this.out.writeUTF(server);
        sender.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
        this.in.readUTF();
        return this.in.readInt();
    }

    public List<Player> playerList(Player sender, String server) {
        this.out.writeUTF("PlayerList");
        this.out.writeUTF(server);
        sender.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
        this.in.readUTF();
        String[] playerListString = this.in.readUTF().split(",");
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (String playerName : playerListString) {
            playerList.add(Bukkit.getPlayerExact(playerName));
        }
        return playerList;
    }

    public void message(Player sender, Player target, String message) {
        this.out = ByteStreams.newDataOutput();
        this.out.writeUTF("Message");
        this.out.writeUTF(target.getDisplayName());
        this.out.writeUTF(message);
        sender.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
    }

    public String getServer(Player target) {
        this.out.writeUTF("GetServer");
        target.sendPluginMessage(this.instance, this.bungeeChannel, this.out.toByteArray());
        return this.in.readUTF();
    }

    public String getUUID(Player target) {
        this.out.writeUTF("UUIDOther");
        this.out.writeUTF(target.getDisplayName());
        this.in.readUTF();
        return this.in.readUTF();
    }
}