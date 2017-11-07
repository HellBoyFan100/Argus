package net.argus.games;

import net.argus.core.component.ParentComponent;
import net.argus.engine.Arena;
import net.argus.engine.components.packet.PacketComponent;
import net.argus.games.hub.HubArena;
import net.argus.games.hub.HubArenaPacket;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.redisson.api.RedissonClient;

import static net.argus.engine.components.event.EventComponent.listen;

public class Server extends ParentComponent implements Listener {

    private final String ERROR_NO_ARENA = "There is nothing to see here! Go on, scram!";
    private final PacketComponent packetComponent;
    private Arena arena;

    public Server(RedissonClient redissonClient, String channel) {
        this.packetComponent = addChild(new PacketComponent(redissonClient, channel));

        addChild(listen(AsyncPlayerPreLoginEvent.class, event -> {
            if (arena == null ||  !arena.isEnabled()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ERROR_NO_ARENA);
            }
        }));
        addChild(listen(PlayerJoinEvent.class, event -> {
            if (arena == null ||  !arena.isEnabled()) {
                event.getPlayer().kickPlayer(ERROR_NO_ARENA);
            }
        }));

        onEnable(() -> {
            packetComponent.addListener(HubArenaPacket.class, packet -> {
                if (arena != null) {
                    return;
                }
                arena = new HubArena();
                arena.enable();
            });
        });
        onDisable(() -> {
            packetComponent.clear();
            arena.disable();
            arena = null;
        });
    }

    public PacketComponent getPacketComponent() {
        return packetComponent;
    }

}
