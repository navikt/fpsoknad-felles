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
public final class OppholdsPeriode extends LukketPeriodeMedVedlegg {

    @NotNull
    private final Oppholdsårsak årsak;

    @JsonCreator
    @Builder
    public OppholdsPeriode(LocalDate fom, LocalDate tom, Oppholdsårsak årsak, List<String> vedlegg) {
        super(fom, tom, vedlegg);
        this.årsak = årsak;
    }
}
