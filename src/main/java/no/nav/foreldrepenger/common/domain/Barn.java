package no.nav.foreldrepenger.common.domain;

import java.time.LocalDate;

import no.nav.foreldrepenger.common.domain.felles.AnnenPart;
import no.nav.foreldrepenger.common.domain.felles.Kjønn;

public record Barn(Fødselsnummer fnr,
                   LocalDate fødselsdato,
                   Navn navn,
                   Kjønn kjønn,
                   AnnenPart annenPart) {
}
