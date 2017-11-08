package net.argus.engine.components.disable;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.function.Predicate;

public class HungerDisabledComponent extends Component implements Listener {

    private final Predicate<HumanEntity> humanEntity;

    public HungerDisabledComponent() {
        this(humanEntity -> true);
    }

    public HungerDisabledComponent(Predicate<HumanEntity> humanEntity) {
        this.humanEntity = humanEntity;

        onEnable(() -> ServerUtil.registerListener(this));
        onDisable(() -> ServerUtil.unregisterListener(this));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (humanEntity.test(event.getEntity())) {
            event.setCancelled(true);
        }
    }

}
