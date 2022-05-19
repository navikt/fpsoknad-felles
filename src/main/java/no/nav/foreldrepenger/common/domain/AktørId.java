package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BARE_TALL;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record AktørId(@JsonValue @Pattern(regexp = BARE_TALL) String value) implements ArbeidsgiverIdentifikator {

    @JsonCreator
    public static AktørId valueOf(String id) {
        return new AktørId(id);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "AktørId{" + "value='" + mask(value) + '\'' + '}';
    }
}
