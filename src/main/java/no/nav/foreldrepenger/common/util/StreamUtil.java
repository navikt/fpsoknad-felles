package no.nav.foreldrepenger.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StreamUtil {

    private static final Logger LOG = LoggerFactory.getLogger(StreamUtil.class);

    private StreamUtil() {
    }

    @SafeVarargs
    public static <T> Stream<T> safeStream(T... elems) {
        return safeStream(List.of(elems));
    }

    public static <T> Stream<T> safeStream(List<T> list) {
        return Optional.ofNullable(list)
            .orElseGet(List::of)
            .stream();
    }

    public static <T> Stream<T> safeStream(Collection<T> set) {
        return Optional.ofNullable(set)
            .orElseGet(Set::of)
            .stream();
    }

    public static <T> List<T> distinct(List<T> list) {
        return safeStream(list).distinct().toList();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <T> T onlyElem(Set<T> set) {
        verifiser(set);
        return safeStream(verifiser(set))
            .map(Optional::ofNullable)
            .findFirst()
            .orElseGet(Optional::empty)
            .orElse(null);
    }

    private static <T> Collection<T> verifiser(Collection<T> collection) {
        if (!collection.isEmpty() && collection.size() != 1) {
            LOG.trace("Mer en ett element i {}", collection);
        }
        return collection;
    }

    public static <T> T onlyElem(List<T> list) {
        verifiser(list);
        return safeStream(list)
            .map(Optional::ofNullable)
            .findFirst()
            .orElseGet(Optional::empty)
            .orElse(null);
    }
}
