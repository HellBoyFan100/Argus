package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.function.Predicate;

public class DropItemDisabledComponent extends Component implements Listener {

    private final Predicate<Player> players;
    private final Predicate<Item> filter;

    public DropItemDisabledComponent() {
        this(players -> true);
    }

    public DropItemDisabledComponent(Predicate<Player> players) {
        this(players, filter -> true);
    }

    public DropItemDisabledComponent(Predicate<Player> players, Predicate<Item> filter) {
        this.players = players;
        this.filter = filter;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemDrop(PlayerDropItemEvent event) {
        if (players.test(event.getPlayer()) && filter.test(event.getItemDrop())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You can't do that!");
        }
    }

}
