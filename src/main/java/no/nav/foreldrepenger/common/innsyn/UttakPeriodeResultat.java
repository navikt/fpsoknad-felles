package no.nav.foreldrepenger.common.innsyn;

public record UttakPeriodeResultat(boolean innvilget, boolean trekkerMinsterett, boolean trekkerDager, Årsak årsak) {

    public enum Årsak {
        ANNET,
        AVSLAG_HULL_MELLOM_FORELDRENES_PERIODER,
    }
}

