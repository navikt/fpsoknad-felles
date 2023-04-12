package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.validation.annotations.LukketPeriode;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;

@LukketPeriode
@JsonPropertyOrder({ "fom", "tom" })
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = OverføringsPeriode.class, name = "overføring"),
        @Type(value = UttaksPeriode.class, name = "uttak"),
        @Type(value = OppholdsPeriode.class, name = "opphold"),
        @Type(value = UtsettelsesPeriode.class, name = "utsettelse")
})
public abstract sealed class LukketPeriodeMedVedlegg implements Comparable<LukketPeriodeMedVedlegg>
        permits OverføringsPeriode,OppholdsPeriode,UtsettelsesPeriode,UttaksPeriode {

    @NotNull
    private final LocalDate fom;
    @NotNull
    private final LocalDate tom;
    private final List<VedleggReferanse> vedlegg;

    @JsonCreator
    protected LukketPeriodeMedVedlegg(LocalDate fom, LocalDate tom, List<VedleggReferanse> vedlegg) {
        this.fom = fom;
        this.tom = tom;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }

    @JsonIgnore
    public long dager() {
        return arbeidsdager(fom, tom) + 1;
    }

    private static long arbeidsdager(final LocalDate start, final LocalDate end) {
        final long days = ChronoUnit.DAYS.between(start, end);
        return days - 2 * ((days + start.getDayOfWeek().getValue()) / 7);
    }

    public LocalDate getFom() {
        return fom;
    }

    public LocalDate getTom() {
        return tom;
    }

    public List<VedleggReferanse> getVedlegg() {
        return vedlegg;
    }

    @Override
    public int compareTo(LukketPeriodeMedVedlegg other) {
        return getFom().compareTo(other.getFom());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LukketPeriodeMedVedlegg that = (LukketPeriodeMedVedlegg) o;
        return Objects.equals(fom, that.fom) && Objects.equals(tom, that.tom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fom, tom);
    }

    @Override
    public String toString() {
        return "LukketPeriodeMedVedlegg{" + "fom=" + fom + ", tom=" + tom + '}';
    }
}
