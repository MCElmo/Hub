package me.mc_elmo.hub.tasks;

import me.mc_elmo.hub.Hub;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoadDataTask
        extends BukkitRunnable {
    private Player player;
    private Hub instance;

    public LoadDataTask(Player player, Hub instance) {
        this.player = player;
        this.instance = instance;
    }

    @Override
    public void run() {
        try {
            String query = "SELECT * FROM `players` WHERE 'uuid' = ?";
            PreparedStatement statement = this.instance.getConnection().prepareStatement(query);
            statement.setString(1, this.player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement pS = this.instance.getConnection().prepareStatement("INSERT INTO `players`(`uuid`, `name`, `ip`) VALUES (?,?,?)");
                pS.setString(1, this.player.getUniqueId().toString());
                pS.setString(2, this.player.getDisplayName());
                pS.setString(3, this.player.getAddress().toString());
                pS.executeUpdate();
                pS.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}