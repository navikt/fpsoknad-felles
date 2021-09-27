package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OverføringsPeriode extends LukketPeriodeMedVedlegg {

    Overføringsårsak årsak;
    StønadskontoType uttaksperiodeType;

    @Builder
    @JsonCreator
    public OverføringsPeriode(LocalDate fom, LocalDate tom, @NotNull Overføringsårsak årsak,
            @NotNull StønadskontoType uttaksperiodeType, List<String> vedlegg) {
        super(fom, tom, vedlegg);
        this.årsak = årsak;
        this.uttaksperiodeType = uttaksperiodeType;
    }
}
