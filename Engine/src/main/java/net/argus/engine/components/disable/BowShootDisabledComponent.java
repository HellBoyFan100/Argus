package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.function.Predicate;

public class BowShootDisabledComponent extends Component implements Listener {

    private final Predicate<Entity> entity;

    public BowShootDisabledComponent() {
        this(entity -> true);
    }

    public BowShootDisabledComponent(Predicate<Entity> entity) {
        this.entity = entity;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBowShoot(EntityShootBowEvent event) {
        if (entity.test(event.getEntity())) {
            event.setCancelled(true);
        }
    }

}
