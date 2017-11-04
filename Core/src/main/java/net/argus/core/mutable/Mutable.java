package net.argus.core.mutable;

import java.util.function.Consumer;

public interface Mutable<T> extends Augmentable<T>, Reducible<T> {

    @Override
    default Mutable<T> onAdd(Consumer<T> listener) {
        return (Mutable<T>) Augmentable.super.onAdd(listener);
    }

    @Override
    default Mutable<T> onAdded(Consumer<T> listener) {
        return (Mutable<T>) Augmentable.super.onAdded(listener);
    }

    @Override
    default Mutable<T> onRemove(Consumer<T> listener) {
        return (Mutable<T>) Reducible.super.onRemove(listener);
    }

    @Override
    default Mutable<T> onRemoved(Consumer<T> listener) {
        return (Mutable<T>) Reducible.super.onRemoved(listener);
    }

}
