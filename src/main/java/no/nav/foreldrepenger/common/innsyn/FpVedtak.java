package no.nav.foreldrepenger.common.innsyn;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FpVedtak(@NotNull List<@NotNull @Valid UttakPeriode> perioder) { }
