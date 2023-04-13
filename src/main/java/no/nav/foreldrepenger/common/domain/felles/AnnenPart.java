package no.nav.foreldrepenger.common.domain.felles;

import java.time.LocalDate;

import jakarta.validation.Valid;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;

public record AnnenPart(@Valid Fødselsnummer fnr, @Valid AktørId aktørId, @Valid Navn navn, LocalDate fødselsdato) {

}
