package no.nav.foreldrepenger.common.domain.felles.opptjening;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

public record AnnenOpptjening(AnnenOpptjeningType type, @Valid ÅpenPeriode periode, List<VedleggReferanse> vedlegg) {

    @JsonCreator
    public AnnenOpptjening {
        vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AnnenOpptjening that = (AnnenOpptjening) o;
        return type == that.type && Objects.equals(periode, that.periode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, periode);
    }

    @Override
    public String toString() {
        return "AnnenOpptjening{" + "type=" + type + ", periode=" + periode + '}';
    }
}
