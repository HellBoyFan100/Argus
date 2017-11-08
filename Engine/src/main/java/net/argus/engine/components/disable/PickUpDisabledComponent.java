package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.function.Predicate;

public class PickUpDisabledComponent extends Component implements Listener {

    private final Predicate<Player> players;

    public PickUpDisabledComponent() {
        this(players -> true);
    }

    public PickUpDisabledComponent(Predicate<Player> players) {
        this.players = players;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPickUpItem(PlayerPickupItemEvent event) {
        if (players.test(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
