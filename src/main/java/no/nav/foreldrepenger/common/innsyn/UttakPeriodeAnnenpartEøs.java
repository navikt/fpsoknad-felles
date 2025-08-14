package no.nav.foreldrepenger.common.innsyn;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UttakPeriodeAnnenpartEøs(@NotNull LocalDate fom,
                                       @NotNull LocalDate tom,
                                       @NotNull KontoType trekkonto,
                                       @NotNull @Min(0) @Max(1000) @Digits(integer = 3, fraction = 1) BigDecimal trekkdager) {
}
