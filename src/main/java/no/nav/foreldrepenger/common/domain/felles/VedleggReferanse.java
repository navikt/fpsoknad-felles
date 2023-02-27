package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record VedleggReferanse(@NotNull @Pattern(regexp = "^[\\p{Digit}\\p{L}-_]*$") @JsonValue String referanse) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public VedleggReferanse {
        // Trengs for å legge på @JsonCreator
    }

    @Override
    public String referanse() {
        return referanse;
    }
}
