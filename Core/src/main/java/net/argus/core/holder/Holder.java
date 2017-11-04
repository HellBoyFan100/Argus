package net.argus.core.holder;

import java.util.function.Predicate;

public interface Holder<T> extends Predicate<T>, Iterable<T> {

    int size();

    @Override
    boolean test(T element);

}
