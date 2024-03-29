package no.nav.foreldrepenger.common.domain.felles;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ÅpenPeriode(@NotNull LocalDate fom, LocalDate tom) {
    public ÅpenPeriode(LocalDate fom) {
        this(fom, null);
    }

}
