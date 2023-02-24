package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.maskListe;

public final class GradertUttaksPeriode extends UttaksPeriode {
    @Valid
    private final ProsentAndel arbeidstidProsent;
    private final boolean erArbeidstaker;
    private final List<@Pattern(regexp = FRITEKST) String> virksomhetsnummer;
    private final boolean arbeidsForholdSomskalGraderes;
    private final Boolean frilans;
    private final Boolean selvstendig;

    @JsonCreator
    public GradertUttaksPeriode(LocalDate fom,
                                LocalDate tom,
                                List<VedleggReferanse> vedlegg,
                                StønadskontoType uttaksperiodeType,
                                boolean ønskerSamtidigUttak,
                                MorsAktivitet morsAktivitetsType,
                                boolean ønskerFlerbarnsdager,
                                ProsentAndel samtidigUttakProsent,
                                ProsentAndel arbeidstidProsent,
                                boolean erArbeidstaker,
                                List<String> virksomhetsnummer,
                                boolean arbeidsForholdSomskalGraderes,
                                Boolean frilans,
                                Boolean selvstendig,
                                Boolean justeresVedFødsel) {
        super(fom, tom, vedlegg, uttaksperiodeType, ønskerSamtidigUttak, morsAktivitetsType, ønskerFlerbarnsdager,
                samtidigUttakProsent, justeresVedFødsel);
        this.arbeidstidProsent = arbeidstidProsent;
        this.erArbeidstaker = erArbeidstaker;
        this.virksomhetsnummer = Optional.ofNullable(virksomhetsnummer).orElse(emptyList());
        this.arbeidsForholdSomskalGraderes = arbeidsForholdSomskalGraderes;
        this.frilans = frilans;
        this.selvstendig = selvstendig;
    }



    public ProsentAndel getArbeidstidProsent() {
        return arbeidstidProsent;
    }

    public boolean isErArbeidstaker() {
        return erArbeidstaker;
    }

    public List<String> getVirksomhetsnummer() {
        return virksomhetsnummer;
    }

    public boolean isArbeidsForholdSomskalGraderes() {
        return arbeidsForholdSomskalGraderes;
    }

    public Boolean getFrilans() {
        return frilans;
    }

    public Boolean getSelvstendig() {
        return selvstendig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GradertUttaksPeriode that = (GradertUttaksPeriode) o;
        return erArbeidstaker == that.erArbeidstaker && arbeidsForholdSomskalGraderes == that.arbeidsForholdSomskalGraderes && Objects.equals(arbeidstidProsent, that.arbeidstidProsent) && Objects.equals(virksomhetsnummer, that.virksomhetsnummer) && Objects.equals(frilans, that.frilans) && Objects.equals(selvstendig, that.selvstendig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), arbeidstidProsent, erArbeidstaker, virksomhetsnummer, arbeidsForholdSomskalGraderes, frilans, selvstendig);
    }

    @Override
    public String toString() {
        return "GradertUttaksPeriode{" +
                "arbeidstidProsent=" + arbeidstidProsent +
                ", erArbeidstaker=" + erArbeidstaker +
                ", virksomhetsnummer=" + maskListe(virksomhetsnummer) +
                ", arbeidsForholdSomskalGraderes=" + arbeidsForholdSomskalGraderes +
                ", frilans=" + frilans +
                ", selvstendig=" + selvstendig +
                '}' + super.toString();
    }
}
