package net.argus.games.hub;

import net.argus.core.holder.BasicCollectionHolder;
import net.argus.core.holder.MutableHolder;
import net.argus.engine.Arena;
import net.argus.engine.components.client.Client;
import net.argus.engine.components.client.ClientComponent;
import net.argus.engine.components.client.ClientRank;
import net.argus.engine.components.command.CommandComponent;
import net.argus.engine.components.disable.DisableComponent;
import net.argus.engine.utility.color.Palette;
import net.argus.engine.utility.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.argus.engine.components.event.EventComponent.listen;

public class HubArena extends Arena {

    private final ClientComponent clientComponent;
    private final CommandComponent commandComponent;
    private final MutableHolder<Player> players;

    public HubArena() {
        players = new BasicCollectionHolder<>();

        addChild(clientComponent = new ClientComponent());
        addChild(commandComponent = new CommandComponent());
        addChild(listen(PlayerJoinEvent.class, (event -> {
            Player player = event.getPlayer();
            if (player.getName().equals("FaultyRam")) {
                clientComponent.getClients().add(new Client(player.getUniqueId(), player.getName(), ClientRank.DEVELOPER));
            } else {
                clientComponent.getClients().add(new Client(player.getUniqueId(), player.getName(), ClientRank.FRIEND));
            }
            players.add(player);
        })));
        addChild(listen(PlayerQuitEvent.class, (event -> {
            Player player = event.getPlayer();
            clientComponent.getClients().remove(clientComponent.getClient(player.getUniqueId()));
            players.remove(player);
        })));
        addChild(DisableComponent.dropItem(player -> true), DisableComponent.blockBreak());

        commandComponent.onPlayerCommand(command -> command.contains("join"), ((player, args) -> {
            player.sendMessage(StringUtil.format(Palette.WHITE_PINK, "{1}Hey there, {2}" + player.getName() + "{1}! You may now speak."));
        }));

        onEnable(() -> {
            clientComponent.enable();
            commandComponent.enable();
        });
        onDisable(() -> {
            clientComponent.enable();
            commandComponent.disable();
        });
    }


}
