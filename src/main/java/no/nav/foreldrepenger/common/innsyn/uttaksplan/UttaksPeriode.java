package no.nav.foreldrepenger.common.innsyn.uttaksplan;

import java.time.LocalDate;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.MorsAktivitet;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Oppholdsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType;

public record UttaksPeriode(Oppholdsårsak oppholdAarsak,
                            Overføringsårsak overfoeringAarsak,
                            GraderingAvslagÅrsak graderingAvslagAarsak,
                            UtsettelsePeriodeType utsettelsePeriodeType,
                            PeriodeResultatType periodeResultatType,
                            Boolean graderingInnvilget,
                            Boolean samtidigUttak,
                            LocalDate fom,
                            LocalDate tom,
                            @Valid LukketPeriode periode,
                            @JsonAlias("trekkonto") StønadskontoType stønadskontotype,
                            Double trekkDager,
                            Integer arbeidstidprosent,
                            Integer utbetalingsprosent,
                            Boolean gjelderAnnenPart,
                            MorsAktivitet morsAktivitet,
                            Boolean flerbarnsdager,
                            Boolean manueltBehandlet,
                            Integer samtidigUttaksprosent,
                            UttakArbeidType uttakArbeidType,
                            ArbeidsgiverInfo arbeidsgiverInfo,
                            String periodeResultatÅrsak) implements Comparable<UttaksPeriode>{

    public UttaksPeriode {
        periode = new LukketPeriode(fom, tom);
    }

    @Override
    public int compareTo(UttaksPeriode other) {
        return this.periode().fom().compareTo(other.periode().fom());
    }
}
