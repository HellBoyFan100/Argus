package net.argus.engine.components.client;

import net.argus.core.component.Component;
import net.argus.core.holder.BasicCollectionHolder;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ClientComponent extends Component {

    private final BasicCollectionHolder<Client> clients = new BasicCollectionHolder<>();

    public ClientComponent() {
        onEnable(() -> ServerUtil.getOnlinePlayers().forEach(player -> clients.add(new Client(player.getUniqueId(), player.getName(), ClientRank.DONOR))));
        onDisable(() -> clients.forEach(clients::remove));
    }

    public Client getClient(UUID uuid) {
        for (Client client : clients) {
            if (uuid.equals(client.getUUID())) {
                return client;
            }
        }
        return null;
    }

    public BasicCollectionHolder<Client> getClients() {
        return clients;
    }

}
