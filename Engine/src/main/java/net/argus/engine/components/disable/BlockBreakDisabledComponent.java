package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.function.Predicate;

public class BlockBreakDisabledComponent extends Component implements Listener {

    private final Predicate<Player> players;
    private final Predicate<Block> filter;

    public BlockBreakDisabledComponent() {
        this(player -> true);
    }

    public BlockBreakDisabledComponent(Predicate<Player> players) {
        this(players, filter -> true);
    }

    public BlockBreakDisabledComponent(Predicate<Player> players, Predicate<Block> filter) {
        this.players = players;
        this.filter = filter;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        if (players.test(event.getPlayer()) && filter.test(event.getBlock())) {
            event.setCancelled(true);
        }
    }

}
