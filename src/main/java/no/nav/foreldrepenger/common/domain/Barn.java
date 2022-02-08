package no.nav.foreldrepenger.common.domain;

import java.time.LocalDate;

import javax.validation.Valid;

import no.nav.foreldrepenger.common.domain.felles.AnnenPart;
import no.nav.foreldrepenger.common.domain.felles.Kjønn;

public record Barn(@Valid Fødselsnummer fnr,
                   LocalDate fødselsdato,
                   @Valid Navn navn,
                   Kjønn kjønn,
                   @Valid AnnenPart annenPart) {
}
