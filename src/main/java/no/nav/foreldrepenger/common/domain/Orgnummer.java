package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.util.StringUtil.partialMask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.domain.validation.OrgnrValidator;

public record Orgnummer(@JsonValue String value) implements ArbeidsgiverIdentifikator{

    public static final String MAGIC = "342352362";

    public static final Orgnummer MAGIC_ORG = Orgnummer.valueOf(MAGIC);

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Orgnummer {
        if (!validator().isValid(value, null)) {
            throw new IllegalArgumentException(value + " er ikke et gyldig organisasjonsnummer");
        }
    }

    // For å bare ha @JsonValue på fields, og ikke getter...
    @Override
    public String value() {
        return value;
    }

    private OrgnrValidator validator() {
        return new OrgnrValidator();
    }

    public static Orgnummer valueOf(String orgnr) {
        return new Orgnummer(orgnr);
    }

    public String maskert() {
        return value != null ? value.substring(0, 5) + "****" : "<null>";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [orgnr=" + partialMask(value, 9) + "]";
    }
}
