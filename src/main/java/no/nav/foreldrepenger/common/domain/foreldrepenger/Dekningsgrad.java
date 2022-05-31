package no.nav.foreldrepenger.common.domain.foreldrepenger;

import static java.lang.Integer.parseInt;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.error.UnexpectedInputException;

public enum Dekningsgrad {

    ÅTTI(80),
    HUNDRE(100);

    @JsonValue
    private final int kode;

    Dekningsgrad(int kode) {
        this.kode = kode;
    }

    public int kode() {
        return kode;
    }

    public static Dekningsgrad fraKode(String kode) {
        return fraKode(parseInt(kode));
    }

    public static Dekningsgrad fraKode(int kode) {
        return Arrays.stream(Dekningsgrad.values())
                .filter(e -> e.kode == kode)
                .findFirst()
                .orElseThrow(() -> new UnexpectedInputException("Ikke støttet dekningsgrad %s.", kode));
    }
}
