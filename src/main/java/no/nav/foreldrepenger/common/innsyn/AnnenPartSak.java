package no.nav.foreldrepenger.common.innsyn;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AnnenPartSak(@NotNull List<UttakPeriode> perioder, LocalDate termindato, @NotNull Dekningsgrad dekningsgrad, @NotNull int antallBarn) {
}
