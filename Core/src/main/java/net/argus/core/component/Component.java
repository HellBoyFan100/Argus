package net.argus.core.component;

import net.argus.core.Named;
import net.argus.core.enableable.Enableable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Component implements Enableable, Named {

    private boolean enabled = false;
    private String name = "Unnamed Component";

    private final List<Runnable> enableListenable = new ArrayList<>();
    private final List<Runnable> disableListenable = new ArrayList<>();

    @Override
    public Component enable() {
        if (enabled) return this;

        enableListenable.forEach(Runnable::run);
        enabled = true;
        return this;
    }

    @Override
    public Component disable() {
        if (!enabled) return this;

        disableListenable.forEach(Runnable::run);
        enabled = false;
        return this;
    }

    protected Component onEnable(Runnable... listeners) {
        if (listeners.length > 1) {
            enableListenable.addAll(Arrays.asList(listeners));
        } else {
            enableListenable.add(listeners[0]);
        }
        return this;
    }

    protected Component onDisable(Runnable... listeners) {
        if (listeners.length > 1) {
            disableListenable.addAll(Arrays.asList(listeners));
        } else {
            disableListenable.add(listeners[0]);
        }
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

}
