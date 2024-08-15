package no.nav.foreldrepenger.common.domain.foreldrepenger;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public LocalDate getFørsteUttaksdag() {
        return fordeling.getFørsteUttaksdag();
    }

    @JsonIgnore
    public LocalDate getFørsteInntektsmeldingDag() {
        return Optional.ofNullable(getFørsteUttaksdag())
                .map(d -> d.minusWeeks(4))
                .orElse(null);
    }
}
