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
public final class FriUtsettelsesPeriode extends UtsettelsesPeriode {

    @JsonCreator
    @Builder(builderMethodName = "FriUtsettelsesPeriodeBuilder")
    public FriUtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker,
            @NotNull UtsettelsesÅrsak årsak, StønadskontoType type, MorsAktivitet morsAktivitetsType, List<String> vedlegg) {
        super(fom, tom, erArbeidstaker, null, årsak, type, morsAktivitetsType, vedlegg);
    }

}
