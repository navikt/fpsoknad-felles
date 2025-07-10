package no.nav.foreldrepenger.common.innsyn;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record UttakPeriodeAnnenpartEøs(@NotNull LocalDate fom,
                                       @NotNull LocalDate tom,
                                       @NotNull KontoType kontoType,
                                       @NotNull BigDecimal trekkdager) {
}
