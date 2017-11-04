package net.argus.engine.components.redisson;

import net.argus.core.component.Component;

@SuppressWarnings("unchecked")
public class RedissonComponent extends Component {

    public RedissonComponent() {
        setName("Redisson");
        onEnable();
        onDisable();
    }

}
