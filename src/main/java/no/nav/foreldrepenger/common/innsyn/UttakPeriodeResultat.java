package no.nav.foreldrepenger.common.innsyn;

public record UttakPeriodeResultat(boolean innvilget, boolean trekkerMinsterett, boolean trekkerDager, Årsak årsak) {

    public enum Årsak {
        ANNET,
        AVSLAG_HULL_MELLOM_FORELDRENES_PERIODER,
        AVSLAG_FRATREKK_PLEIEPENGER,
        AVSLAG_UTSETTELSE_TILBAKE_I_TID,
        INNVILGET_UTTAK_AVSLÅTT_GRADERING_TILBAKE_I_TID,
    }
}

