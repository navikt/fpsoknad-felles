package no.nav.foreldrepenger.common.domain.foreldrepenger;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Foreldrepenger extends Ytelse {

    @Valid
    private AnnenForelder annenForelder;
    @Valid
    private final RelasjonTilBarn relasjonTilBarn;
    @Valid
    private final Rettigheter rettigheter;
    private final Dekningsgrad dekningsgrad;
    @Valid
    private final Opptjening opptjening;
    @Valid
    private final Fordeling fordeling;
    @Valid
    private final Medlemsskap medlemsskap;

}
