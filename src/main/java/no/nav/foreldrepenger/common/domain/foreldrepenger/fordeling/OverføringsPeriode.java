package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;


public final class OverføringsPeriode extends LukketPeriodeMedVedlegg {

    @NotNull
    private final Overføringsårsak årsak;
    @NotNull
    private final StønadskontoType uttaksperiodeType;

    @JsonCreator
    public OverføringsPeriode(LocalDate fom, LocalDate tom, Overføringsårsak årsak, StønadskontoType uttaksperiodeType) {
        super(fom, tom);
        this.årsak = årsak;
        this.uttaksperiodeType = uttaksperiodeType;
    }


    public Overføringsårsak getÅrsak() {
        return årsak;
    }

    public StønadskontoType getUttaksperiodeType() {
        return uttaksperiodeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OverføringsPeriode that = (OverføringsPeriode) o;
        return årsak == that.årsak && uttaksperiodeType == that.uttaksperiodeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), årsak, uttaksperiodeType);
    }

    @Override
    public String toString() {
        return "OverføringsPeriode{" +
                "årsak=" + årsak +
                ", uttaksperiodeType=" + uttaksperiodeType +
                '}' + super.toString();
    }
}
