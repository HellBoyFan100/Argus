package net.argus.engine.components.client;

import net.argus.core.component.Component;
import net.argus.core.holder.BasicCollectionHolder;
import net.argus.engine.utility.ServerUtil;
import net.argus.engine.utility.StringUtil;
import net.argus.engine.utility.color.Palette;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClientComponent extends Component {

    private final BasicCollectionHolder<Client> clients = new BasicCollectionHolder<>();

    public ClientComponent() {
        clients.onAdd(client -> {
            Player player = ServerUtil.getPlayer(client.getUUID());
            player.setPlayerListName(client.getRank().getFormat() + " " + client.getName());
            System.out.println("Adding " + client.getName() + " to clients.");
        });
        clients.onAdded(client -> {
            ServerUtil.getPlayer(client.getUUID()).sendMessage(StringUtil.format(this, Palette.WHITE_PINK, "{1}You've been added to {2}clients{1}."));
            System.out.println("Added " + client.getName() + " to clients. " + client.toString());
        });
        clients.onRemove(client -> {
            System.out.println("Removing " + client.getName() + " from clients.");
        });
        clients.onRemoved(client -> {
            System.out.println("Removed " + client.getName() + " from clients. " + client.toString());
        });
    }

    public Client getClient(UUID uuid) {
        return clients.getContents().stream().filter(client -> uuid.equals(client.getUUID())).findAny().orElse(null);
    }

    public BasicCollectionHolder<Client> getClients() {
        return clients;
    }

}
