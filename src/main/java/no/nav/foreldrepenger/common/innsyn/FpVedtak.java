package no.nav.foreldrepenger.common.innsyn;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record FpVedtak(@NotNull List<@NotNull @Valid UttakPeriode> perioder, List<@NotNull @Valid UttakPeriodeAnnenpartEøs> perioderAnnenpartEøs) {}
