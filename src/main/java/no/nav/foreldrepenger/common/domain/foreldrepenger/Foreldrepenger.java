package no.nav.foreldrepenger.common.domain.foreldrepenger;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;

public record Foreldrepenger(@Valid AnnenForelder annenForelder,
                             @Valid RelasjonTilBarn relasjonTilBarn,
                             @Valid Rettigheter rettigheter,
                             Dekningsgrad dekningsgrad,
                             @Valid Opptjening opptjening,
                             @Valid Fordeling fordeling,
                             @Valid Utenlandsopphold utenlandsopphold) implements Ytelse {
}
