package no.nav.foreldrepenger.common.innsyn.svp;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OppholdPeriode(@NotNull LocalDate fom, @NotNull LocalDate tom, @NotNull Årsak årsak, @NotNull OppholdKilde oppholdKilde) {
    public enum Årsak {
        SYKEPENGER,
        FERIE
    }
    public enum OppholdKilde {
        SAKSBEHANDLER,
        INNTEKTSMELDING,
        SØKNAD
    }
}
