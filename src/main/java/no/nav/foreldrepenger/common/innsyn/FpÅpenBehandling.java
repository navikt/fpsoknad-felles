package no.nav.foreldrepenger.common.innsyn;

import java.util.List;
import java.util.Objects;

public record FpÅpenBehandling(BehandlingTilstand tilstand, List<UttakPeriode> søknadsperioder) {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FpÅpenBehandling that = (FpÅpenBehandling) o;
        return tilstand == that.tilstand && erListeneLike(søknadsperioder, that.søknadsperioder);
    }

    private boolean erListeneLike(List<UttakPeriode> l1, List<UttakPeriode> l2) {
        if (l1 == null && l2 == null)
            return true;
        if (l1 == null || l2 == null)
            return false;
        return l1.size() == l2.size() && l2.containsAll(l1);
    }


    @Override
    public int hashCode() {
        return Objects.hash(tilstand, søknadsperioder);
    }
}
