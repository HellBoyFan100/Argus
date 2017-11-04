package net.argus.core.phase;

import net.argus.core.completable.Completeable;
import net.argus.core.component.Component;

import java.util.ArrayList;
import java.util.List;

public class Phase extends Component implements Completeable {

    private final List<Runnable> completeListeners = new ArrayList<>();
    private boolean complete = false;

    public Phase() {
        onComplete(() -> {
            System.out.println("Completed " + getName());
            this.disable();
        });
    }

    @Override
    public Phase enable() {
        if (!isEnabled()) {
            complete = false;
        }
        super.enable();
        return this;
    }

    @Override
    public void complete() {
        if (complete || !isEnabled()) {
            return;
        }
        if (!isEnabled()) {
            complete = true;
        }
        completeListeners.forEach(Runnable::run);
    }

    @Override
    public boolean isCompleted() {
        return complete;
    }

    public Phase onComplete(Runnable listener) {
        completeListeners.add(listener);
        return this;
    }

}
