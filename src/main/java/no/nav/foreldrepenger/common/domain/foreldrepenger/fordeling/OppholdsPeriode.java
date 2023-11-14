package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;

public final class OppholdsPeriode extends LukketPeriodeMedVedlegg {

    @NotNull
    private final Oppholdsårsak årsak;

    @JsonCreator
    public OppholdsPeriode(LocalDate fom, LocalDate tom, Oppholdsårsak årsak) {
        super(fom, tom);
        this.årsak = årsak;
    }

    public Oppholdsårsak getÅrsak() {
        return årsak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OppholdsPeriode that = (OppholdsPeriode) o;
        return årsak == that.årsak;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), årsak);
    }

    @Override
    public String toString() {
        return "OppholdsPeriode{" +
                "årsak=" + årsak +
                '}' + super.toString();
    }
}
