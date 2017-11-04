package net.argus.core.phase;

import net.argus.core.enableable.Enableable;
import net.argus.core.enableable.ParentEnableable;

import java.util.HashSet;
import java.util.Set;

public class ParentPhase extends Phase implements ParentEnableable {

    private final Set<Enableable> children = new HashSet<>();

    public ParentPhase() {
        onEnable(() -> System.out.println("Enabled " + getName()));
        onDisable(() -> System.out.println("Disabled " + getName()));
    }

    @Override
    public ParentPhase enable() {
        super.enable();
        ParentEnableable.super.enable();
        return this;
    }

    @Override
    public ParentPhase disable() {
        super.disable();
        ParentEnableable.super.disable();
        return this;
    }

    @Override
    public Set<Enableable> getChildren() {
        return children;
    }

}
