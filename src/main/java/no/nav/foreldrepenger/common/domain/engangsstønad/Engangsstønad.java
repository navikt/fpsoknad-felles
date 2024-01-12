package no.nav.foreldrepenger.common.domain.engangsstønad;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

@JsonPropertyOrder({ "utenlandsopphold", "relasjonTilBarn" })
public record Engangsstønad(@Valid Utenlandsopphold utenlandsopphold,
                            @Valid RelasjonTilBarn relasjonTilBarn) implements Ytelse {

}
