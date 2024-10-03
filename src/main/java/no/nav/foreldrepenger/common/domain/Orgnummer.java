package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.validation.annotations.Orgnr;

public record Orgnummer(@JsonValue @Pattern(regexp = FRITEKST) @NotNull @Orgnr String value) implements ArbeidsgiverIdentifikator {
    public static final Orgnummer MAGIC_ORG = new Orgnummer("889640782");

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Orgnummer { // NOSONAR
    }

    public String maskert() {
        return value != null ? mask(value) : "<null>";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [orgnr=" + mask(value) + "]";
    }
}
