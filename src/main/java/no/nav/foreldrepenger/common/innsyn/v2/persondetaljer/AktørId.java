package no.nav.foreldrepenger.common.innsyn.v2.persondetaljer;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import no.nav.foreldrepenger.common.innsyn.v2.PersonDetaljer;


public record AktørId(String value) implements PersonDetaljer {

    @JsonCreator
    public AktørId {
        Objects.requireNonNull(value, "AktørId kan ikke være null");
    }

    @JsonProperty("aktørId")
    public String value() {
        return value;
    }

}
