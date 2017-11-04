package net.argus.core.mutable;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.Consumer;

public interface Reducible<T> {

    List<Consumer<T>> getRemoveListeners();
    List<Consumer<T>> getRemovedListeners();

    boolean removeSilently(T element);

    @SuppressWarnings("unchecked")
    default boolean remove(Object element) {
        ImmutableList.copyOf(getRemoveListeners()).forEach(tConsumer -> tConsumer.accept((T) element));
        boolean result = removeSilently((T) element);
        ImmutableList.copyOf(getRemovedListeners()).forEach(tConsumer -> tConsumer.accept((T) element));
        return result;
    }

    default boolean remove(Iterable<Object> elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed | remove(element);
        return removed;
    }

    default boolean remove(Object[] elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed | remove(element);
        return removed;
    }


    default Reducible<T> onRemove(Consumer<T> listener) {
        getRemoveListeners().add(listener);
        return this;
    }

    default Reducible<T> onRemoved(Consumer<T> listener) {
        getRemovedListeners().add(listener);
        return this;
    }

}
