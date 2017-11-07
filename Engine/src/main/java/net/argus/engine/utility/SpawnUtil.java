package net.argus.engine.utility;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Consumer;

public class SpawnUtil implements Consumer<Player> {

    private final List<Vector> spawns;
    private int index;

    public SpawnUtil(List<Vector> spawns) {
        this.spawns = spawns;
    }

    @Override
    public void accept(Player player) {
        if (spawns.size() < 1) {
            player.kickPlayer("Couldn't find a place to spawn you!");
        } else {
            player.teleport(spawns.get(index > spawns.size() - 1 ? index = 0 : index++).toLocation(player.getWorld()));
        }
    }

}
