package no.nav.foreldrepenger.common.util;

import static java.util.function.Predicate.not;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class StringUtil {
    private static final String DEFAULT_FLERTALL = "er";
    private static final int DEFAULT_LENGTH = 50;

    private StringUtil() {
    }

    public static String taint(String value) {
        if (!value.matches("[a-zA-Z0-9]++"))
            throw new IllegalArgumentException(value);
        return value;
    }

    public static String limit(String tekst) {
        return limit(tekst, DEFAULT_LENGTH);
    }

    public static String limit(String tekst, int max) {
        return Optional.ofNullable(tekst)
            .filter(t -> t.length() >= max)
            .map(s -> s.substring(0, max - 1) + "...")
            .orElse(tekst);
    }

    public static String limit(byte[] bytes) {
        return limit(bytes, DEFAULT_LENGTH);
    }

    public static String limit(byte[] bytes, int max) {
        return limit(Arrays.toString(bytes), max);
    }

    public static String limitLast(String tekst, int max) {
        return Optional.ofNullable(tekst)
                .filter(t -> t.length() >= max)
                .map(s -> "*".repeat(s.length() - max) + s.substring(s.length() - max))
                .orElse(tekst);
    }

    public static String partialMask(String value) {
        return partialMask(value, 6);
    }

    public static String partialMask(String value, int maskFraIndex) {
        return Optional.ofNullable(value)
                .filter(t -> t.length() >= maskFraIndex)
                .map(s -> s.substring(0, maskFraIndex) + "*".repeat(s.length() - maskFraIndex))
                .orElse(value);

    }

    public static List<String> maskListe(List<String> values) {
        return safeStream(values)
                .map(StringUtil::mask)
                .toList();
    }

    public static String encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
    }

    public static String mask(String value) {
        return Optional.ofNullable(value)
            .map(String::stripLeading)
            .filter(not(String::isBlank))
            .map(v -> "*".repeat(v.length()))
            .orElse("<null>");
    }

    public static String endelse(List<?> liste) {
        if (liste == null || liste.isEmpty()) {
            return "er";
        }
        return liste.size() == 1 ? "" : "er";
    }

    public static String flertall(List<?> liste) {
        var størrelse = liste != null ? liste.size() : 0;
        return flertall(størrelse, DEFAULT_FLERTALL);
    }

    public static String flertall(int n) {
        return flertall(n, DEFAULT_FLERTALL);
    }

    public static String flertall(int n, String flertall) {
        if (n != 1) {
            return flertall;
        }
        return "";
    }

    public static String storeForbokstaver(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        var words = text.trim().split("\\s");
        return Arrays.stream(words)
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .map(n -> Character.toUpperCase(n.charAt(0)) + n.substring(1))
                .collect(Collectors.joining(" "));
    }

}
