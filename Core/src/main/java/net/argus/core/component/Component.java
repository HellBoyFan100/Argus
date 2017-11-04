package net.argus.core.component;

import net.argus.core.Named;
import net.argus.core.enableable.Enableable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Component implements Enableable, Named {

    private final List<Runnable> enableListenable = new ArrayList<>();
    private final List<Runnable> disableListenable = new ArrayList<>();
    private boolean enabled = false;
    private String name = "Unnamed";

    @Override
    public Component enable() {
        if (enabled) {
            return this;
        }
        enabled = true;
        enableListenable.forEach(Runnable::run);
        System.out.println(getName() + " has been enabled.");
        return this;
    }

    @Override
    public Component disable() {
        if (!enabled) {
            return this;
        }
        enabled = false;
        disableListenable.forEach(Runnable::run);
        System.out.println(getName() + " has been disabled");
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return name + "Component";
    }

    public Component onEnable(Runnable listener) {
        enableListenable.add(listener);
        return this;
    }

    public Component onDisable(Runnable listener) {
        disableListenable.add(listener);
        return this;
    }

    public Component setName(String name) {
        this.name = name;
        return this;
    }

}
