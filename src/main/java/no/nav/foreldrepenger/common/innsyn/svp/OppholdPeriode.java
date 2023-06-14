package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

public record OppholdPeriode(LocalDate fom, LocalDate tom, Årsak årsak) implements Periode {
    public enum Årsak {
        SYKEPENGER,
        FERIE
    }
}
