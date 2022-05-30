package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.domain.validation.annotations.Orgnr;

public record Orgnummer(@JsonValue @Pattern(regexp = FRITEKST) @Orgnr String value) implements ArbeidsgiverIdentifikator {
    public static final String MAGIC = "342352362";
    public static final Orgnummer MAGIC_ORG = Orgnummer.valueOf(MAGIC);

    @JsonCreator
    public static Orgnummer valueOf(String id) {
        return new Orgnummer(id);
    }

    @Override
    public String value() { // NOSONAR: Her overrider vi default getter fra record fordi den propagerer annoteringer fra field. Vi ønsker ikke @JsonValue på getter.
        return value;
    }

    public String maskert() {
        return value != null ? mask(value) : "<null>";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [orgnr=" + mask(value) + "]";
    }
}
