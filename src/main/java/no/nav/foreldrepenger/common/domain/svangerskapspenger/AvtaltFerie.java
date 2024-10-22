package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import java.time.LocalDate;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Arbeidsforhold;

public record AvtaltFerie(@Valid Arbeidsforhold arbeidsforhold, LocalDate ferieFom, LocalDate ferieTom) {
}
