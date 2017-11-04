package net.argus.games.hub;

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

    public HubArena() {
        ClientComponent clientComponent = new ClientComponent();
        CommandComponent commandComponent = new CommandComponent();

        addChild(listen(PlayerJoinEvent.class, (event -> {
            Player player = event.getPlayer();
            if (player.getName().equals("FaultyRam")) {
                clientComponent.getClients().add(new Client(player.getUniqueId(), player.getName(), ClientRank.DEVELOPER));
            } else {
                clientComponent.getClients().add(new Client(player.getUniqueId(), player.getName(), ClientRank.FRIEND));
            }
        })));
        addChild(listen(PlayerQuitEvent.class, (event -> {
            Player player = event.getPlayer();
            clientComponent.getClients().remove(clientComponent.getClient(player.getUniqueId()));
        })));
        addChild(DisableComponent.dropItem(player -> true), DisableComponent.blockBreak());

        commandComponent.onPlayerCommand(command -> command.contains("hey"), ((player, args) -> {
            player.sendMessage(StringUtil.format(Palette.WHITE_PINK, "{1}Hey there, {2}" + player.getName() + "{1}!"));
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
