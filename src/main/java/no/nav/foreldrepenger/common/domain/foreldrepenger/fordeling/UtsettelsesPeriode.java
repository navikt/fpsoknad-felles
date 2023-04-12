package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = FriUtsettelsesPeriode.class, name = "fri")
})
public sealed class UtsettelsesPeriode extends LukketPeriodeMedVedlegg permits FriUtsettelsesPeriode {

    @NotNull
    private final UtsettelsesÅrsak årsak;
    private final boolean erArbeidstaker;
    private final MorsAktivitet morsAktivitetsType;

    @JsonCreator
    public UtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker, UtsettelsesÅrsak årsak,
                              MorsAktivitet morsAktivitetsType, List<VedleggReferanse> vedlegg) {
        super(fom, tom, vedlegg);
        this.erArbeidstaker = erArbeidstaker;
        this.årsak = årsak;
        this.morsAktivitetsType = morsAktivitetsType;
    }

    public UtsettelsesÅrsak getÅrsak() {
        return årsak;
    }

    public boolean isErArbeidstaker() {
        return erArbeidstaker;
    }

    public MorsAktivitet getMorsAktivitetsType() {
        return morsAktivitetsType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        UtsettelsesPeriode that = (UtsettelsesPeriode) o;
        return erArbeidstaker == that.erArbeidstaker && årsak == that.årsak;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), årsak, erArbeidstaker);
    }

    @Override
    public String toString() {
        return "UtsettelsesPeriode{"
                + "årsak=" + årsak + ", "
                + "erArbeidstaker=" + erArbeidstaker + ", "
                + "morsAktivitetsType=" + morsAktivitetsType + "} "
                + super.toString();
    }
}
