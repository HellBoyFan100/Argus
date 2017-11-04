package net.argus.engine.components.command;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class CommandComponent extends Component implements Listener {

    private final Map<Predicate<String>, BiConsumer<Player, String[]>> commands = new HashMap<>();

    public CommandComponent() {
        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    public Predicate<String> onPlayerCommand(Predicate<String> filter, BiConsumer<Player, String[]> listener) {
        if (commands.containsKey(filter)) {
            commands.remove(filter);
        } else {
            commands.put(filter, listener);
        }
        return filter;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage().substring(1);
        commands.forEach((key, value) -> {
            if (key.test(message)) {
                event.setCancelled(true);
                value.accept(event.getPlayer(), event.getMessage().split(" "));
            }
        });
    }


}
