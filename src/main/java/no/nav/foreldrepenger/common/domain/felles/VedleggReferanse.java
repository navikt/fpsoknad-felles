package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VedleggReferanse(@NotNull @Pattern(regexp = "^[\\p{Digit}\\p{L}-_]*$") @JsonValue String referanse) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public VedleggReferanse { // NOSONAR
    }
}
