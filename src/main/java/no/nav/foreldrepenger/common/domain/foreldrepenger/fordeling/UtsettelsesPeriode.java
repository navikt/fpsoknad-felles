package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.maskListe;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true, exclude = { "morsAktivitetsType", "virksomhetsnummer" })
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = FriUtsettelsesPeriode.class, name = "fri")
})
public sealed class UtsettelsesPeriode extends LukketPeriodeMedVedlegg permits FriUtsettelsesPeriode {

    @NotNull
    private final UtsettelsesÅrsak årsak;
    private final boolean erArbeidstaker;
    private final List<@Pattern(regexp = FRITEKST) String> virksomhetsnummer;
    private final MorsAktivitet morsAktivitetsType;

    @JsonCreator
    @Builder(builderMethodName = "UtsettelsesPeriodeBuilder")
    public UtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker, List<String> virksomhetsnummer,
                              UtsettelsesÅrsak årsak, MorsAktivitet morsAktivitetsType, List<String> vedlegg) {
        super(fom, tom, vedlegg);
        this.erArbeidstaker = erArbeidstaker;
        this.virksomhetsnummer = virksomhetsnummer;
        this.årsak = årsak;
        this.morsAktivitetsType = morsAktivitetsType;
    }

    @Override
    public String toString() {
        return "UtsettelsesPeriode{"
                + "årsak=" + årsak + ", "
                + "erArbeidstaker=" + erArbeidstaker + ", "
                + "virksomhetsnummer=" + maskListe(virksomhetsnummer) + ", "
                + "morsAktivitetsType=" + morsAktivitetsType + "} "
                + super.toString();
    }
}
