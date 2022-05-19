package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

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
import lombok.ToString;

@Getter
@ToString(callSuper = true, exclude = "virksomhetsnummer")
@EqualsAndHashCode(callSuper = true, exclude = { "morsAktivitetsType", "virksomhetsnummer" })
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = FriUtsettelsesPeriode.class, name = "fri")
})
public sealed class UtsettelsesPeriode extends LukketPeriodeMedVedlegg permits FriUtsettelsesPeriode {

    @NotNull
    private final UtsettelsesÅrsak årsak;
    @NotNull
    private final StønadskontoType uttaksperiodeType;
    private final boolean erArbeidstaker;
    private final List<@Pattern(regexp = FRITEKST) String> virksomhetsnummer;
    private final MorsAktivitet morsAktivitetsType;

    @JsonCreator
    @Builder(builderMethodName = "UtsettelsesPeriodeBuilder")
    public UtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker, List<String> virksomhetsnummer,
                              UtsettelsesÅrsak årsak, StønadskontoType uttaksperiodeType,
                              MorsAktivitet morsAktivitetsType,
                              List<String> vedlegg) {
        super(fom, tom, vedlegg);
        this.erArbeidstaker = erArbeidstaker;
        this.virksomhetsnummer = virksomhetsnummer;
        this.årsak = årsak;
        this.uttaksperiodeType = uttaksperiodeType;
        this.morsAktivitetsType = morsAktivitetsType;
    }
}
