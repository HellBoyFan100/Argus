package net.argus.engine.components.event;

import net.argus.core.component.Component;
import net.argus.engine.utility.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class EventComponent<T extends Event> extends Component implements Listener {

    private final List<Consumer<T>> eventListeners = new ArrayList<>();
    private final Class<T> type;
    private final EventPriority priority;

    public EventComponent(Class<T> type, EventPriority priority) {
        this.type = type;
        this.priority = priority;
    }

    public EventComponent onEvent(Consumer<T> listener) {
        eventListeners.add(listener);
        return this;
    }

    @Override
    public EventComponent<T> enable() {
        Bukkit.getPluginManager().registerEvent(type, this, priority, (listener, event) -> {
            if (event != null) {
                eventListeners.forEach(tConsumer -> tConsumer.accept((T) event));
            }
        }, Bukkit.getPluginManager().getPlugins()[0]);
        super.enable();
        return this;
    }

    @Override
    public EventComponent<T> disable() {
        ServerUtil.unregisterListener(this);
        super.disable();
        return this;
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type) {
        return listen(type, null, null);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, Consumer<T> listener) {
        return listen(type, null, listener);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, EventPriority priority) {
        return listen(type, priority, null);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, EventPriority priority, Consumer<T> listener) {
        if (priority == null) {
            priority = EventPriority.NORMAL;
        }

        EventComponent<T> event = new EventComponent<>(type, priority);
        if (!Event.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Sorry, couldn't resolve Event through method that returns functional interfaces.\n" +
                    "Please either use listen(Consumer<T> listener, Predicate<T> filter, Class<T> type) or use direct method references or lambdas");
        }
        if (listener != null) {
            event.onEvent(listener);
        }
        return event;
    }

    public List<Consumer<T>> getEventListeners() {
        return eventListeners;
    }

}
