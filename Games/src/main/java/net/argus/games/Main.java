package net.argus.games;

import net.argus.games.hub.HubArenaPacket;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

public class Main extends JavaPlugin {

    private final RedissonClient redissonClient;
    private final Server server;

    public Main() {
        redissonClient = Redisson.create();
        server = new Server(redissonClient, "Server." + getServer().getPort());
        RList<Vector> list = redissonClient.getList("hub-spawns");
        list.clear();
        list.add(new Vector(0.5, 158, 0.5));
    }

    @Override
    public void onEnable() {
        server.enable();
        server.getPacketComponent().send(new HubArenaPacket());
    }

    @Override
    public void onDisable() {
        server.disable();
    }

}
