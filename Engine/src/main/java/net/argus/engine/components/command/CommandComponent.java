package net.argus.engine.components.command;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommandComponent extends Component implements Listener {

    private final Map<Predicate<String>, Consumer<String[]>> consoleCommands = new HashMap<>();
    private final Map<Predicate<String>, BiConsumer<Player, String[]>> playerCommands = new HashMap<>();

    public CommandComponent() {
        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    public Predicate<String> onConsoleCommand(Predicate<String> filter, Consumer<String[]> listener) {
        if (consoleCommands.containsKey(filter)) {
            consoleCommands.remove(filter);
        } else {
            consoleCommands.put(filter, listener);
        }
        return filter;
    }

    public Predicate<String> onPlayerCommand(Predicate<String> filter, BiConsumer<Player, String[]> listener) {
        if (playerCommands.containsKey(filter)) {
            playerCommands.remove(filter);
        } else {
            playerCommands.put(filter, listener);
        }
        return filter;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onConsoleCommand(ServerCommandEvent event) {
        String[] message = event.getCommand().split(" ");
        consoleCommands.forEach((key, value) -> {
            if (key.test(message[0])) {
                event.setCancelled(true);
                value.accept(message);
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage().substring(1);
        playerCommands.forEach((key, value) -> {
            if (key.test(message)) {
                event.setCancelled(true);
                value.accept(event.getPlayer(), event.getMessage().split(" "));
            }
        });
    }


}
