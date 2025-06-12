package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AnnenPartSak(@NotNull List<@Valid @NotNull UttakPeriode> perioder, LocalDate termindato, @NotNull DekningsgradSak dekningsgrad, @NotNull int antallBarn) {
}
