package net.argus.engine.components.chat;

import net.argus.core.component.Component;
import net.argus.core.holder.Holder;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import static net.argus.core.Extensions.function;

public class ChatComponent extends Component implements Listener {

    private final BiFunction<Player, Holder<Player>, String> formatter;
    private final Map<UUID, Holder<Player>> selected = new HashMap<>();

    public ChatComponent(BiFunction<Player, Holder<Player>, String> formatter) {
        this.formatter = formatter;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    public void select(UUID uuid, Holder<Player> channel) {
        selected.put(uuid, channel);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);
        if (!selected.containsKey(player.getUniqueId())) {
            return;
        }

        Holder<Player> channel = selected.get(player.getUniqueId());
        channel.forEach(players -> {
            players.sendMessage(formatter.andThen(function(String::format, new Object[]{player.getName(), event.getMessage()})).apply(players, channel));
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (selected.containsKey(player)) {
            selected.remove(player);
        }
    }

}
