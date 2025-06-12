package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record OppholdPeriode(@NotNull LocalDate fom, @NotNull LocalDate tom, @NotNull OppholdPeriode.OppholdÅrsak årsak, @NotNull OppholdKilde oppholdKilde) {
    public enum OppholdÅrsak {
        SYKEPENGER,
        FERIE
    }
    public enum OppholdKilde {
        SAKSBEHANDLER,
        INNTEKTSMELDING,
        SØKNAD
    }
}
