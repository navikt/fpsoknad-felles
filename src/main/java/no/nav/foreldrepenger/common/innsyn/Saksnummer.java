package no.nav.foreldrepenger.common.innsyn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public record Saksnummer(@NotNull @JsonValue String value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Saksnummer { // NOSONAR
    }
}
