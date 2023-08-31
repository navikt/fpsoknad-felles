package no.nav.foreldrepenger.common.util;

import java.util.Arrays;

public enum AuthenticationLevel {
    NONE,
    IDPORTEN_LOA_SUBSTANTIAL,
    IDPORTEN_LOA_HIGH,
    LEVEL3,
    LEVEL4;

    public static AuthenticationLevel of(String level) {
        return Arrays.stream(values())
                .filter(v -> v.name().replace("_", "-").equalsIgnoreCase(level))
                .findFirst()
                .orElse(NONE);
    }
}
