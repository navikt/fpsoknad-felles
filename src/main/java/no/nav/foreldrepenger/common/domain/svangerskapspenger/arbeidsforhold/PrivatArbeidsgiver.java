package no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;

public record PrivatArbeidsgiver(@NotNull @Valid Fødselsnummer fnr) implements Arbeidsforhold {

}
