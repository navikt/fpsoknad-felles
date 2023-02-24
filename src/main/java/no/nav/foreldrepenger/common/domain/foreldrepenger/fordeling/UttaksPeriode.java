package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;


@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = GradertUttaksPeriode.class, name = "gradert")
})
public sealed class UttaksPeriode extends LukketPeriodeMedVedlegg permits GradertUttaksPeriode {
    private final StønadskontoType uttaksperiodeType;
    private final boolean ønskerSamtidigUttak;
    private final MorsAktivitet morsAktivitetsType;
    private final boolean ønskerFlerbarnsdager;
    private final ProsentAndel samtidigUttakProsent;
    private final Boolean justeresVedFødsel;

    @JsonCreator
    public UttaksPeriode(LocalDate fom, LocalDate tom, List<VedleggReferanse> vedlegg, StønadskontoType uttaksperiodeType, boolean ønskerSamtidigUttak,
                         MorsAktivitet morsAktivitetsType, boolean ønskerFlerbarnsdager, ProsentAndel samtidigUttakProsent,
                         Boolean justeresVedFødsel) {
        super(fom, tom, vedlegg);
        this.uttaksperiodeType = uttaksperiodeType;
        this.ønskerSamtidigUttak = ønskerSamtidigUttak;
        this.morsAktivitetsType = morsAktivitetsType;
        this.ønskerFlerbarnsdager = ønskerFlerbarnsdager;
        this.samtidigUttakProsent = samtidigUttakProsent;
        this.justeresVedFødsel = justeresVedFødsel;
    }

    @JsonProperty
    public StønadskontoType getUttaksperiodeType() {
        return uttaksperiodeType;
    }

    public boolean isØnskerSamtidigUttak() {
        return ønskerSamtidigUttak;
    }

    public MorsAktivitet getMorsAktivitetsType() {
        return morsAktivitetsType;
    }

    public boolean isØnskerFlerbarnsdager() {
        return ønskerFlerbarnsdager;
    }

    public ProsentAndel getSamtidigUttakProsent() {
        return samtidigUttakProsent;
    }

    public Boolean getJusteresVedFødsel() {
        return justeresVedFødsel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UttaksPeriode that = (UttaksPeriode) o;
        return ønskerSamtidigUttak == that.ønskerSamtidigUttak && ønskerFlerbarnsdager == that.ønskerFlerbarnsdager && uttaksperiodeType == that.uttaksperiodeType && morsAktivitetsType == that.morsAktivitetsType && Objects.equals(samtidigUttakProsent, that.samtidigUttakProsent) && Objects.equals(justeresVedFødsel, that.justeresVedFødsel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uttaksperiodeType, ønskerSamtidigUttak, morsAktivitetsType, ønskerFlerbarnsdager, samtidigUttakProsent, justeresVedFødsel);
    }

    @Override
    public String toString() {
        return "UttaksPeriode{" +
                "uttaksperiodeType=" + uttaksperiodeType +
                ", ønskerSamtidigUttak=" + ønskerSamtidigUttak +
                ", morsAktivitetsType=" + morsAktivitetsType +
                ", ønskerFlerbarnsdager=" + ønskerFlerbarnsdager +
                ", samtidigUttakProsent=" + samtidigUttakProsent +
                ", justeresVedFødsel=" + justeresVedFødsel +
                '}' + super.toString();
    }
}
