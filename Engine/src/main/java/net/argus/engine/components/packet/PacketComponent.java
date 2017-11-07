package net.argus.engine.components.packet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.argus.core.component.Component;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PacketComponent extends Component {

    private final Multimap<Class<?>, Consumer<?>> listeners = ArrayListMultimap.create();
    private final RTopic<Object> topic;
    private final Predicate<Object> packets;
    private int listener;

    public PacketComponent(RedissonClient redissonClient, String channel) {
        this(redissonClient, channel, packets -> true);
    }

    public PacketComponent(RedissonClient redissonClient, String channel, Predicate<Object> packets) {
        topic = redissonClient.getTopic(channel);
        this.packets = packets;

        onEnable(() -> {
            listener = topic.addListener((chan, packet) -> {
                if (packets.test(packet)) {
                    listeners.get(packet.getClass()).forEach(listener -> ((Consumer<Object>) listener).accept(packet));
                }
            });
        });
        onDisable(() -> {
            topic.removeListener(listener);
            clear();
        });
    }

    public void send(Object packet) {
        topic.publish(packet);
    }

    public <T> void addListener(Class<T> type, Consumer<T> listener) {
        listeners.put(type, listener);
    }

    public void removeListener(Class<?> type, Consumer<?> listener) {
        listeners.asMap().remove(type, listener);
    }

    public void clear() {
        listeners.clear();
    }

}
