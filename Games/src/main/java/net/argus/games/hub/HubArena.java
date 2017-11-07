package net.argus.games.hub;

import net.argus.core.holder.BasicCollectionHolder;
import net.argus.core.holder.MutableHolder;
import net.argus.engine.Arena;
import net.argus.engine.utility.SpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import static net.argus.engine.components.event.EventComponent.listen;

public class HubArena extends Arena {

    private final MutableHolder<Player> players;

    public HubArena(RedissonClient redissonClient) {
        RList<Vector> spawns = redissonClient.getList("hub-spawns");

        players = new BasicCollectionHolder<>();
        players.onAdd(new SpawnUtil(spawns.readAll()));
        addChild(listen(PlayerJoinEvent.class, event -> {
            players.add(event.getPlayer());
        }));
        addChild(listen(PlayerQuitEvent.class, event -> {
            players.remove(event.getPlayer());
        }));
    }


}
