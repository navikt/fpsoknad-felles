package no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.Orgnummer;

public record Virksomhet(@NotNull @Valid Orgnummer orgnr) implements Arbeidsforhold {

}
