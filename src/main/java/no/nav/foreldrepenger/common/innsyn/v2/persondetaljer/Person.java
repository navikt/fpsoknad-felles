package no.nav.foreldrepenger.common.innsyn.v2.persondetaljer;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.innsyn.v2.PersonDetaljer;


public record Person(Fødselsnummer fnr,
                     String fornavn,
                     String mellomnavn,
                     String etternavn,
                     Kjønn kjønn,
                     LocalDate fødselsdato) implements PersonDetaljer {

    @JsonCreator
    public Person {
        Objects.requireNonNull(fnr,"Fnr kan ikke være null");
        Objects.requireNonNull(fornavn,"Fornavn kan ikke være null");
        Objects.requireNonNull(fornavn,"Etternavn kan ikke være null");
    }

}
