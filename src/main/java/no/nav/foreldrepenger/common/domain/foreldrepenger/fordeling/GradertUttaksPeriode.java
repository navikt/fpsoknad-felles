package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;

@Getter
@EqualsAndHashCode(callSuper = true, exclude = { "virksomhetsnummer" })
@ToString(callSuper = true, exclude = { "virksomhetsnummer" })
public final class GradertUttaksPeriode extends UttaksPeriode {
    @Valid
    private final ProsentAndel arbeidstidProsent;
    private final boolean erArbeidstaker;
    private final List<@Pattern(regexp = FRITEKST) String> virksomhetsnummer;
    private final boolean arbeidsForholdSomskalGraderes;
    private final Boolean frilans;
    private final Boolean selvstendig;

    @JsonCreator
    @Builder(builderMethodName = "GradertUttaksPeriodeBuilder")
    public GradertUttaksPeriode(LocalDate fom,
                                LocalDate tom,
                                List<String> vedlegg,
                                @JsonAlias("UttaksperiodeType") StønadskontoType uttaksperiodeType, // TODO: Fjern alias etter ferdig expand
                                boolean ønskerSamtidigUttak,
                                MorsAktivitet morsAktivitetsType,
                                boolean ønskerFlerbarnsdager,
                                ProsentAndel samtidigUttakProsent,
                                ProsentAndel arbeidstidProsent,
                                boolean erArbeidstaker,
                                List<String> virksomhetsnummer,
                                boolean arbeidsForholdSomskalGraderes,
                                Boolean frilans,
                                Boolean selvstendig) {
        super(fom, tom, vedlegg, uttaksperiodeType, ønskerSamtidigUttak, morsAktivitetsType, ønskerFlerbarnsdager,
                samtidigUttakProsent);
        this.arbeidstidProsent = arbeidstidProsent;
        this.erArbeidstaker = erArbeidstaker;
        this.virksomhetsnummer = Optional.ofNullable(virksomhetsnummer).orElse(emptyList());
        this.arbeidsForholdSomskalGraderes = arbeidsForholdSomskalGraderes;
        this.frilans = frilans;
        this.selvstendig = selvstendig;
    }
}
