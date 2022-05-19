package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import no.nav.foreldrepenger.common.domain.Orgnummer;

public record Virksomhet(@NotNull @Valid Orgnummer orgnr) implements Arbeidsforhold {

}
