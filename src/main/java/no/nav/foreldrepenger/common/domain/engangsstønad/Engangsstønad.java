package no.nav.foreldrepenger.common.domain.engangsstønad;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

import javax.validation.Valid;

@JsonPropertyOrder({ "medlemsskap", "relasjonTilBarn" })
public record Engangsstønad(@Valid Medlemsskap medlemsskap, @Valid RelasjonTilBarn relasjonTilBarn) implements Ytelse {

}
