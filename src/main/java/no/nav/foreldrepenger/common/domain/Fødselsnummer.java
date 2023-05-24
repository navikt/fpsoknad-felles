package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.felles.Kjønn;

public record Fødselsnummer(@Pattern(regexp = FRITEKST) @NotNull @JsonValue String value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Fødselsnummer { // NOSONAR
    }

    public Kjønn kjønn() {
        if (value != null && value.length() == 11) {
            return value.charAt(8) % 2 == 0 ? Kjønn.K : Kjønn.M;
        }
        return Kjønn.U;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [fnr=" + mask(value) + "]";
    }
}
