package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record AnnenOpptjening(AnnenOpptjeningType type, @Valid ÅpenPeriode periode, List<@Pattern(regexp = FRITEKST) String> vedlegg) {

    @JsonCreator
    public AnnenOpptjening(AnnenOpptjeningType type, ÅpenPeriode periode, List<String> vedlegg) {
        this.type = type;
        this.periode = periode;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
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
