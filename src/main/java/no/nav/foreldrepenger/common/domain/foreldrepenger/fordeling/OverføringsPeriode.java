package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class OverføringsPeriode extends LukketPeriodeMedVedlegg {

    @NotNull
    private final Overføringsårsak årsak;
    @NotNull
    private final StønadskontoType uttaksperiodeType;

    @Builder
    @JsonCreator
    public OverføringsPeriode(LocalDate fom, LocalDate tom, Overføringsårsak årsak,
            StønadskontoType uttaksperiodeType, List<String> vedlegg) {
        super(fom, tom, vedlegg);
        this.årsak = årsak;
        this.uttaksperiodeType = uttaksperiodeType;
    }
}
