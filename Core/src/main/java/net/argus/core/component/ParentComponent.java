package net.argus.core.component;

import net.argus.core.enableable.Enableable;
import net.argus.core.enableable.ParentEnableable;

import java.util.HashSet;
import java.util.Set;

public class ParentComponent extends Component implements ParentEnableable {

    private final Set<Enableable> children = new HashSet<>();

    public ParentComponent(Enableable... children) {
        addChild(children);
    }

    @Override
    public ParentComponent enable() {
        super.enable();
        ParentEnableable.super.enable();
        return this;
    }

    @Override
    public ParentComponent disable() {
        super.disable();
        ParentEnableable.super.disable();
        return this;
    }

    @Override
    public Set<Enableable> getChildren() {
        return children;
    }

}
