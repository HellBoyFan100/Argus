package net.argus.games.hub;

import net.argus.core.holder.BasicCollectionHolder;
import net.argus.core.holder.MutableHolder;
import net.argus.engine.Arena;
import net.argus.engine.components.client.Client;
import net.argus.engine.components.client.ClientComponent;
import net.argus.engine.components.disable.*;
import net.argus.engine.utility.SpawnUtil;
import net.argus.engine.utility.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import static net.argus.engine.components.event.EventComponent.listen;

public class HubArena extends Arena {

    private final ItemStack gameSelector = new ItemBuilder(Material.COMPASS).name(ChatColor.LIGHT_PURPLE + "Game Selector").flags(ItemFlag.HIDE_ATTRIBUTES).build();
    private final ItemStack collectibles = new ItemBuilder(Material.TOTEM).name(ChatColor.LIGHT_PURPLE + "Collectibles").flags(ItemFlag.HIDE_ATTRIBUTES).build();

    private final ClientComponent clientComponent;
    private final MutableHolder<Player> players;

    public HubArena(RedissonClient redissonClient) {
        RList<Vector> spawns = redissonClient.getList("hub-spawns");

        addChild(clientComponent = new ClientComponent(redissonClient));
        players = new BasicCollectionHolder<>();
        addChild(listen(PlayerJoinEvent.class, event -> {
            Player player = event.getPlayer();
            players.add(player);

            Client client = clientComponent.getClientFromList(player.getUniqueId());
            if (client == null) {
                clientComponent.getClients().add(clientComponent.createClient(player.getUniqueId(), player.getName()));
            } else {
                clientComponent.getClients().add(client);
            }
        }));
        addChild(listen(PlayerQuitEvent.class, event -> {
            Player player = event.getPlayer();
            players.remove(player);
            clientComponent.getClients().remove(clientComponent.saveClient(player.getUniqueId()));
        }));
        players.onAdd(new SpawnUtil(spawns.readAll()));
        players.onAdd(player -> {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.getInventory().setItem(0, gameSelector);
            player.getInventory().setItem(8, collectibles);
        });

        addChild(
                new PvEDisabledComponent(),
                new BlockBreakDisabledComponent(),
                new BlockPlaceDisabledComponent(),
                new PvPDisabledComponent(),
                new HungerDisabledComponent(),
                new PickUpDisabledComponent(),
                new DropItemDisabledComponent(),
                new BowShootDisabledComponent()
        );
    }


}
