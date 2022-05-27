package no.nav.foreldrepenger.common.domain.foreldrepenger;

import com.fasterxml.jackson.annotation.JsonValue;

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
}
