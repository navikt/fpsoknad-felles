package no.nav.foreldrepenger.common.domain.engangsstønad;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonPropertyOrder({ "medlemsskap", "relasjonTilBarn", "annenForelder" })
public final class Engangsstønad extends Ytelse {

    @Valid
    private final Medlemsskap medlemsskap;
    @Valid
    private AnnenForelder annenForelder;
    @Valid
    private final RelasjonTilBarn relasjonTilBarn;

    @JsonCreator
    public Engangsstønad(@JsonProperty("medlemsskap") Medlemsskap medlemsskap,
            @JsonProperty("relasjonTilBarn") RelasjonTilBarn relasjonTilBarn) {
        this.medlemsskap = medlemsskap;
        this.relasjonTilBarn = relasjonTilBarn;
    }
}
