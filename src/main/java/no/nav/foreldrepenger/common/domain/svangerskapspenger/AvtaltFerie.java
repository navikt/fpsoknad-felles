package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import java.time.LocalDate;

import no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Arbeidsforhold;

public record AvtaltFerie(Arbeidsforhold arbeidsforhold, LocalDate ferieFom, LocalDate ferieTom) {
}
