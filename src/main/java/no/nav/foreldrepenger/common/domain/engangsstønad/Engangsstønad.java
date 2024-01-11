package no.nav.foreldrepenger.common.domain.engangsstønad;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.OppholdIUtlandet;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

import jakarta.validation.Valid;

@JsonPropertyOrder({ "medlemsskap", "relasjonTilBarn" })
public record Engangsstønad(@Deprecated @Valid Medlemsskap medlemsskap,
                            @Valid OppholdIUtlandet utenlandsopphold,
                            @Valid RelasjonTilBarn relasjonTilBarn) implements Ytelse {

}
