package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BARE_TALL;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class AktørId implements ArbeidsgiverIdentifikator {

    @JsonValue
    @Pattern(regexp = BARE_TALL)
    private final String value;

    public AktørId(String id) {
        this.value = id;
    }

    public static AktørId valueOf(String id) {
        return new AktørId(id);
    }

    @Override
    public String value() {
        return value;
    }
}
