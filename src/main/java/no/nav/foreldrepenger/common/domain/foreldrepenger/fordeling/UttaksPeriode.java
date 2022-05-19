package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = GradertUttaksPeriode.class, name = "gradert")
})
public sealed class UttaksPeriode extends LukketPeriodeMedVedlegg permits GradertUttaksPeriode {
    @JsonAlias("UttaksperiodeType") // TODO: Fjern alias etter ferdig expand
    private final StønadskontoType uttaksperiodeType;
    private final boolean ønskerSamtidigUttak;
    private final MorsAktivitet morsAktivitetsType;
    private final boolean ønskerFlerbarnsdager;
    private final ProsentAndel samtidigUttakProsent;

    @JsonCreator
    @Builder(builderMethodName = "UttaksPeriodeBuilder")
    public UttaksPeriode(LocalDate fom, LocalDate tom, List<String> vedlegg, StønadskontoType uttaksperiodeType, boolean ønskerSamtidigUttak,
                         MorsAktivitet morsAktivitetsType, boolean ønskerFlerbarnsdager, ProsentAndel samtidigUttakProsent) {
        super(fom, tom, vedlegg);
        this.uttaksperiodeType = uttaksperiodeType;
        this.ønskerSamtidigUttak = ønskerSamtidigUttak;
        this.morsAktivitetsType = morsAktivitetsType;
        this.ønskerFlerbarnsdager = ønskerFlerbarnsdager;
        this.samtidigUttakProsent = samtidigUttakProsent;
    }
}
