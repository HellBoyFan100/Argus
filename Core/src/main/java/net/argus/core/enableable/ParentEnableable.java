package net.argus.core.enableable;

import net.argus.core.Parent;

public interface ParentEnableable extends Enableable, Parent<Enableable> {

    @Override
    default ParentEnableable enable() {
        getChildren().forEach(Enableable::enable);
        return this;
    }

    @Override
    default ParentEnableable disable() {
        getChildren().forEach(Enableable::disable);
        return this;
    }

    @Override
    default <B extends Enableable> B addChild(B child) {
        if (isEnabled() && !child.isEnabled()) child.enable();

        return Parent.super.addChild(child);
    }

    @Override
    default boolean removeChild(Enableable child) {
        if (isEnabled() && child.isEnabled()) child.disable();

        return Parent.super.removeChild(child);
    }

}
