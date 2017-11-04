package net.argus.engine.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtil {

    private ListUtil() {}

    public static <T> T getRandom(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static <T> List<T> concat(List<T> one, List<T> two) {
        List<T> list = new ArrayList<>(one);
        list.addAll(two);
        return list;
    }

}
