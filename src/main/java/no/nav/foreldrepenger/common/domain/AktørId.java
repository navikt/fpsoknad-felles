package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class AktørId implements ArbeidsgiverIdentifikator {

    @JsonValue
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
