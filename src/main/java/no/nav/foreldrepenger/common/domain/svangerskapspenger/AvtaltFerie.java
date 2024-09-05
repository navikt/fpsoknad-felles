package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

import java.time.LocalDate;

public record AvtaltFerie(Arbeidsforhold arbeidsforhold, LocalDate ferieFom, LocalDate ferieTom) {
}
