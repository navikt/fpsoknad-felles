package no.nav.foreldrepenger.common.innsyn;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FpVedtak(@NotNull List<UttakPeriode> perioder) { }
