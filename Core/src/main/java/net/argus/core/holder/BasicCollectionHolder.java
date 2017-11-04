package net.argus.core.holder;

import java.util.*;
import java.util.function.Consumer;

public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private Set<T> contents = new HashSet<>();

    private List<Consumer<T>> addListeners = new ArrayList<>();
    private List<Consumer<T>> addedListeners = new ArrayList<>();
    private List<Consumer<T>> removeListeners = new ArrayList<>();
    private List<Consumer<T>> removedListeners = new ArrayList<>();

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getAddedListeners() {
        return addedListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public List<Consumer<T>> getRemovedListeners() {
        return removedListeners;
    }

}
