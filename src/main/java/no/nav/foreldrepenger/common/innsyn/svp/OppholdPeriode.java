package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

public record OppholdPeriode(LocalDate fom, Årsak årsak) {
    public enum Årsak {
        SYKEPENGER,
        FERIE
    }
}
