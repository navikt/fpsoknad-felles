package no.nav.foreldrepenger.common.domain.felles;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record VedleggReferanse(@NotNull @Pattern(regexp = "^[\\p{Digit}\\p{L}-_]*$") @JsonValue String referanse) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public VedleggReferanse {
        // Trengs for å legge på @JsonCreator
    }
}
