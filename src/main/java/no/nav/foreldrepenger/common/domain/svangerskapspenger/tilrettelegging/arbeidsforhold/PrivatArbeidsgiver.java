package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;

public record PrivatArbeidsgiver(@NotNull @Valid Fødselsnummer fnr) implements Arbeidsforhold {

}
