package net.argus.core.mutable;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.Consumer;

public interface Augmentable<T> {

    List<Consumer<T>> getAddListeners();
    List<Consumer<T>> getAddedListeners();

    boolean addSilently(T element);

    default boolean add(T element) {
        ImmutableList.copyOf(getAddListeners()).forEach(tConsumer -> tConsumer.accept(element));
        boolean result = addSilently(element);
        ImmutableList.copyOf(getAddedListeners()).forEach(tConsumer -> tConsumer.accept(element));
        return result;
    }

    default boolean add(Iterable<? extends T> elements) {
        boolean added = false;
        for (T element : elements)
            added = added | add(element);
        return added;
    }

    @SuppressWarnings("unchecked")
    default boolean add(T... elements) {
        boolean added = false;
        for (T element : elements)
            added = added | add(element);
        return added;
    }

    default Augmentable<T> onAdd(Consumer<T> listener) {
        getAddListeners().add(listener);
        return this;
    }

    default Augmentable<T> onAdded(Consumer<T> listener) {
        getAddedListeners().add(listener);
        return this;
    }

}
