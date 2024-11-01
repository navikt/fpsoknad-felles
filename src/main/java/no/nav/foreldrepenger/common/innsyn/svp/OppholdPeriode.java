package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

public record OppholdPeriode(LocalDate fom, LocalDate tom, Årsak årsak, OppholdKilde oppholdKilde) {
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
