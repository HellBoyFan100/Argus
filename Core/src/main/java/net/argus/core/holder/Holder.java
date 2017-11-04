package net.argus.core.holder;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Holder<T> extends Predicate<T>, Iterable<T> {

    int size();

    default Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT), false);
    }

    default Stream<T> parallelStream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT), true);
    }

    @Override
    boolean test(T element);

}
