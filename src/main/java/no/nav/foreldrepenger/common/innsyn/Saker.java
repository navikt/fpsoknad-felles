package no.nav.foreldrepenger.common.innsyn;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public record Saker(Set<FpSak> foreldrepenger,
             Set<EsSak> engangsstønad,
             Set<SvpSak> svangerskapspenger) {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Saker saker = (Saker) o;
        return erSetteneLike(foreldrepenger, saker.foreldrepenger) &&
                erSetteneLike(engangsstønad, saker.engangsstønad) &&
                erSetteneLike(svangerskapspenger, saker.svangerskapspenger);
    }

    private boolean erSetteneLike(Set<?> l1, Set<?> l2) {
        var cp = new ArrayList<>( l1 );
        for ( Object o : l2 ) {
            if ( !cp.remove( o ) ) {
                return false;
            }
        }
        return cp.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(foreldrepenger, engangsstønad, svangerskapspenger);
    }
}
