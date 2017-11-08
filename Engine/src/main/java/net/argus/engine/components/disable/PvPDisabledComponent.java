package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.function.Predicate;

public class PvPDisabledComponent extends Component implements Listener {

    private final Predicate<Entity> entity;
    private final Predicate<Entity> attackers;

    public PvPDisabledComponent() {
        this(entity -> true);
    }

    public PvPDisabledComponent(Predicate<Entity> entity) {
        this(entity, entities -> true);
    }

    public PvPDisabledComponent(Predicate<Entity> entity, Predicate<Entity> attackers) {
        this.entity = entity;
        this.attackers = attackers;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (entity.test(event.getEntity()) && attackers.test(event.getDamager())) {
            event.setCancelled(true);
        }
    }

}
