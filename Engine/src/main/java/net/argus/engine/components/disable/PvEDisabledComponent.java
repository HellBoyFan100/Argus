package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.Predicate;

public class PvEDisabledComponent extends Component implements Listener {

    private final Predicate<Entity> entity;
    private final Predicate<EntityDamageEvent.DamageCause> filter;

    public PvEDisabledComponent() {
        this(entity -> true);
    }

    public PvEDisabledComponent(Predicate<Entity> entity) {
        this(entity, filter -> true);
    }

    public PvEDisabledComponent(Predicate<Entity> entity, Predicate<EntityDamageEvent.DamageCause> filter) {
        this.entity = entity;
        this.filter = filter;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event) {
        if (entity.test(event.getEntity()) && filter.test(event.getCause())) {
            event.setCancelled(true);
        }
    }

}
