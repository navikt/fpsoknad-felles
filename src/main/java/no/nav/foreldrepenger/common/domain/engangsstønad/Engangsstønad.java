package no.nav.foreldrepenger.common.domain.engangsstønad;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

@Builder
@JsonPropertyOrder({ "medlemsskap", "relasjonTilBarn" })
public record Engangsstønad(@Valid Medlemsskap medlemsskap, @Valid RelasjonTilBarn relasjonTilBarn) implements Ytelse {

}
