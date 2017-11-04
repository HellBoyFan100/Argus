package net.argus.engine.components.spectate;

import net.argus.core.component.Component;
import net.argus.core.holder.BasicCollectionHolder;
import net.argus.engine.utility.*;
import net.argus.engine.utility.color.Palette;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class SpectateComponent extends Component implements Listener {

    private final BasicCollectionHolder<Player> spectators = new BasicCollectionHolder<>();

    private static final float SPEED_INCREMENT = 0.1f;
    private final List<Vector> spawns;

    public SpectateComponent(List<Vector> spawns) {
        this.spawns = spawns;

        spectators.onAdd(this::start);
        spectators.onAdded(player -> player.sendMessage(StringUtil.format(this, Palette.WHITE_PINK,
                "{1}You're now a {2}spectator{1}.")));
        spectators.onRemove(this::stop);
        spectators.onRemoved(player -> player.sendMessage(StringUtil.format(this, Palette.WHITE_PINK,
                "{1}You're no longer a {2}spectator{1}.")));

        setName("Spectate");
        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    private void start(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setVelocity(new Vector());
        player.getInventory().setHeldItemSlot(0);
        player.getActivePotionEffects().forEach(potionEffect ->  player.removePotionEffect(potionEffect.getType()));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, true, false));

        player.teleport(player.getLocation().add(0, 10, 0));
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.getInventory().setItemInOffHand(new ItemStack(Material.ARROW, 1));
        player.setFlySpeed(0.3f);
    }

    private void stop(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setVelocity(new Vector());
        player.setAllowFlight(false);
        player.setFlying(false);
        //player.teleport(ListUtil.getRandom(spawns).toLocation(player.getWorld()));
        player.getActivePotionEffects().forEach(potionEffect ->  player.removePotionEffect(potionEffect.getType()));

        player.setFlySpeed(0.1f);
        player.getInventory().clear();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onScroll(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (!spectators.test(player)) {
            return;
        }

        event.setCancelled(true);
        if (event.getNewSlot() == 0) {
            return;
        }

        float speed = player.getFlySpeed() + (event.getNewSlot() < 5 ? -SPEED_INCREMENT : SPEED_INCREMENT);
        if (speed > 1.05f || speed < SPEED_INCREMENT + 0.05f) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 2);
            return;
        }

        player.getInventory().getItemInOffHand().setAmount((int) (speed * 10));
        player.setFlySpeed(NumberUtil.bound(speed, 1f, 0.1f));
    }

    public BasicCollectionHolder<Player> getSpectators() {
        return spectators;
    }

}
