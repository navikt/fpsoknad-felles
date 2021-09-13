package no.nav.foreldrepenger.common.oppslag.pdl.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.domain.felles.AnnenPart;

@Builder
@Data
public class BarnDTO {
    private final Fødselsnummer fnr;
    private final Fødselsnummer fnrSøker;
    private final LocalDate fødselsdato;
    private final Navn navn;
    private final AnnenPart annenPart;
}
