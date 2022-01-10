package no.nav.foreldrepenger.common.domain.felles;

import java.time.LocalDate;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;

public record AnnenPart(Fødselsnummer fnr, AktørId aktørId, Navn navn, LocalDate fødselsdato) {

}
