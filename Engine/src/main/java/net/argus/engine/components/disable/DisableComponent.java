package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.components.event.EventComponent;
import net.argus.engine.utility.BlockUtil;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Predicate;

public class DisableComponent extends Component {


    //--COMPONENTS--
    //-Block Place-
    public static EventComponent blockPlace() {
        return blockPlaceBlockFilter(player -> true, block -> true);
    }

    public static EventComponent blockPlace(Predicate<Player> players) {
        return blockPlaceBlockFilter(players, block -> true);
    }

    public static EventComponent blockPlace(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockPlaceBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }

    public static EventComponent blockPlaceBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockPlaceEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock())) {
                event.setCancelled(true);
            }
        });
    }

    //-Block Break-
    public static EventComponent blockBreak() {
        return blockBreakBlockFilter(player -> true, block -> true);
    }

    public static EventComponent blockBreak(Predicate<Player> players) {
        return blockBreakBlockFilter(players, block -> true);
    }

    public static EventComponent blockBreak(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockBreakBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }

    public static EventComponent blockBreakBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockBreakEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock())) {
                event.setCancelled(true);
            }
        });
    }

    //-Pick Up Item-
    public static EventComponent itemPickup() {
        return itemPickupItemFilter(player -> true, item -> true);
    }

    public static EventComponent itemPickup(Predicate<Player> players) {
        return itemPickupItemFilter(players, item -> true);
    }

    public static EventComponent itemPickup(Predicate<Player> players, Predicate<MaterialData> filter) {
        return itemPickupItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }

    public static EventComponent itemPickupItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return itemPickupItemFilter(players, item -> filter.test(item.getItemStack()));
    }

    public static EventComponent itemPickupItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerPickupItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItem())) {
                event.setCancelled(true);
            }
        });
    }

    //-Drop Item-
    public static EventComponent dropItem(Predicate<Player> players) {
        return dropItemItemFilter(players, item -> true);
    }

    public static EventComponent dropItem(Predicate<Player> players, Predicate<MaterialData> filter) {
        return dropItemItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }

    public static EventComponent dropItemItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return dropItemItemFilter(players, item -> filter.test(item.getItemStack()));
    }

    public static EventComponent dropItemItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerDropItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItemDrop())) {
                event.setCancelled(true);
            }
        });
    }

    //-PVP-
    public static EventComponent pvp(Predicate<Player> players) {
        return pvp(players, player -> true);
    }

    public static EventComponent pvp(Predicate<Player> players, Predicate<Player> attackers) {
        return damage(players, event -> event instanceof EntityDamageByEntityEvent
                && ((EntityDamageByEntityEvent) event).getDamager() instanceof Player
                && attackers.test((Player) ((EntityDamageByEntityEvent) event).getDamager())
        );
    }

    //-Damage-
    public static EventComponent damage(Predicate<Player> players) {
        return damage(players, event -> true);
    }

    public static EventComponent damage(Predicate<Player> players, EntityDamageEvent.DamageCause cause) {
        return damage(players, event -> event.getCause().equals(cause));
    }

    public static EventComponent damage(Predicate<Player> players, Predicate<EntityDamageEvent> filter) {
        return EventComponent.listen(EntityDamageEvent.class, event -> {
            if (filter.test(event) && event.getEntity() instanceof Player && players.test((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }

    //-Hunger Change-
    public static EventComponent hungerChange(Predicate<Player> players) {
        return EventComponent.listen(FoodLevelChangeEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }

    //-Bow-
    public static EventComponent bowShoot(Predicate<Player> players) {
        return EventComponent.listen(EntityShootBowEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }


    //--GAME RULE--

    public static Predicate<World> daylightCycle(String value) {
        return setGameRule("doDaylightCycle", value);
    }

    public static Predicate<World> entityDrops(String value) {
        return setGameRule("doEntityDrops", value);
    }

    public static Predicate<World> fireSpread(String value) {
        return setGameRule("doFireTick", value);
    }

    public static Predicate<World> mobLoot(String value) {
        return setGameRule("doMobLoot", value);
    }

    public static Predicate<World> mobSpawning(String value) {
        return setGameRule("doMobSpawning", value);
    }

    public static Predicate<World> mobGriefing(String value) {
        return setGameRule("mobGriefing", value);
    }

    public static Predicate<World> naturalRegeneration(String value) {
        return setGameRule("naturalRegeneration", value);
    }

    public static Predicate<World> randomTickSpeed(String value) {
        return setGameRule("randomTickSpeed", value);
    }

    public static Predicate<World> deathMessages(String value) {
        return setGameRule("showDeathMessages", value);
    }

    public static Predicate<World> setGameRule(String rule, String value) {
        return world -> world.setGameRuleValue(rule, value);
    }

}
