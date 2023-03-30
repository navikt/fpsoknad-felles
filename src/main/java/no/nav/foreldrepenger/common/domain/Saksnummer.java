package no.nav.foreldrepenger.common.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Saksnummer(@JsonValue @NotNull @Digits(integer = 18, fraction = 0) String value) {

    @JsonCreator
    public static Saksnummer valueOf(String id) {
        return new Saksnummer(id);
    }

    @Override
    public String value() {
        return value;
    }
}
