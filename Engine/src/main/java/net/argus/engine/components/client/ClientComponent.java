package net.argus.engine.components.client;

import net.argus.core.component.Component;
import net.argus.core.holder.BasicCollectionHolder;
import net.argus.engine.utility.ServerUtil;
import net.argus.engine.utility.StringUtil;
import net.argus.engine.utility.color.Palette;
import org.bukkit.entity.Player;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import java.util.UUID;

public class ClientComponent extends Component {

    private final RListMultimap<UUID, Client> clientList;
    private final BasicCollectionHolder<Client> clients = new BasicCollectionHolder<>();

    public ClientComponent(RedissonClient redissonClient) {
        clientList = redissonClient.getListMultimap("clients");

        clients.onAdded(client -> {
            Player player = ServerUtil.getPlayer(client.getUUID());
            player.setPlayerListName(client.getRank().getFormat() + " " + client.getName());
        });
    }

    public Client createClient(UUID uuid, String name) {
        Client client = new Client(uuid, name, name.equals("FaultyRam") ? ClientRank.DEVELOPER : ClientRank.USER);
        clientList.put(uuid, client);
        return client;
    }

    public Client saveClient(UUID uuid) {
        Client client = getClient(uuid);
        clientList.get(uuid).set(0, client);
        return client;
    }

    public Client getClient(UUID uuid) {
        return clients.getContents().stream().filter(client -> uuid.equals(client.getUUID())).findAny().orElse(null);
    }

    public Client getClientFromList(UUID uuid) {
        return clientList.get(uuid).stream().findAny().orElse(null);
    }

    public BasicCollectionHolder<Client> getClients() {
        return clients;
    }

}
