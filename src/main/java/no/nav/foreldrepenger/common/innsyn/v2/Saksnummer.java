package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Saksnummer(@JsonValue String value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Saksnummer {
        Objects.requireNonNull(value, "saksnummer kan ikke være null");
    }

    @Override
    public String value() {
        return value;
    }
}
