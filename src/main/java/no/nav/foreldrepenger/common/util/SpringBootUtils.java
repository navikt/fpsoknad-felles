package no.nav.foreldrepenger.common.util;

import java.util.Collection;

public class SpringBootUtils {

    private SpringBootUtils() {
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
