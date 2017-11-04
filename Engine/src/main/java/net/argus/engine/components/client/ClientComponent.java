package net.argus.engine.components.client;

import net.argus.core.component.Component;
import net.argus.core.holder.BasicCollectionHolder;
import net.argus.engine.utility.ServerUtil;
import net.argus.engine.utility.StringUtil;
import net.argus.engine.utility.color.Palette;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Stream;

public class ClientComponent extends Component {

    private final BasicCollectionHolder<Pair<UUID, Client>> clients = new BasicCollectionHolder<>();

    public ClientComponent() {
        clients.onAdd(pair -> {
            Player player = ServerUtil.getPlayer(pair.getKey());
            Client client = pair.getValue();
            player.setPlayerListName(client.getRank().getFormat() + " " + client.getName());
            System.out.println("Adding " + client.getName() + " to clients.");
        });
        clients.onAdded(pair -> {
            Client client = pair.getValue();
            ServerUtil.getPlayer(pair.getKey()).sendMessage(StringUtil.format(this, Palette.WHITE_PINK, "{1}You've been added to {2}clients{1}."));
            System.out.println("Added " + client.getName() + " to clients. " + client.toString());
        });
        clients.onRemove(pair -> {
            System.out.println("Removing " + pair.getValue().getName() + " from clients.");
        });
        clients.onRemoved(pair -> {
            Client client = pair.getValue();
            System.out.println("Removed " + client.getName() + " from clients. " + client.toString());
        });
        onDisable(() -> clients.forEach(clients::remove));
    }

    public Client getClient(UUID uuid) {
        Stream<Pair<UUID, Client>> stream = clients.stream().filter(pair -> uuid.equals(pair.getKey()));
        return (Client) stream.toArray()[0];
    }

    public BasicCollectionHolder<Pair<UUID, Client>> getClients() {
        return clients;
    }

}
