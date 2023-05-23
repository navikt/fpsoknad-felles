package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.validation.annotations.Orgnr;

public record Orgnummer(@JsonValue @Pattern(regexp = FRITEKST) @Orgnr String value) implements ArbeidsgiverIdentifikator {
    public static final String MAGIC = "342352362";
    public static final Orgnummer MAGIC_ORG = new Orgnummer(MAGIC);

    public String maskert() {
        return value != null ? mask(value) : "<null>";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [orgnr=" + mask(value) + "]";
    }
}
