package no.nav.foreldrepenger.common.innsyn;

import java.util.Objects;

public record UttakPeriodeResultat(boolean innvilget, boolean trekkerMinsterett, boolean trekkerDager, Årsak årsak) {

    public enum Årsak {
        ANNET,
        AVSLAG_HULL_MELLOM_FORELDRENES_PERIODER,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UttakPeriodeResultat that = (UttakPeriodeResultat) o;
        return innvilget == that.innvilget &&
                erTrekkerMinstrettLikNårTrekkdagerErTrue(that) &&
                trekkerDager == that.trekkerDager &&
                årsak == that.årsak;
    }

    // TODO: Fjern etter sammenligning!
    private boolean erTrekkerMinstrettLikNårTrekkdagerErTrue(UttakPeriodeResultat that) {
        if (!trekkerDager || !that.trekkerDager) {
            return true;
        }
        return trekkerMinsterett == that.trekkerMinsterett;
    }

    @Override
    public int hashCode() {
        return Objects.hash(innvilget, trekkerMinsterett, trekkerDager, årsak);
    }
}

