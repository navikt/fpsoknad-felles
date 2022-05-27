package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public record AnnenPart(@JsonValue PersonDetaljer personDetaljer) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public AnnenPart {
        Objects.requireNonNull(personDetaljer, "Persondetaljer kan ikke være null");
    }

    @Override
    public PersonDetaljer personDetaljer() {
        return personDetaljer;
    }
}
