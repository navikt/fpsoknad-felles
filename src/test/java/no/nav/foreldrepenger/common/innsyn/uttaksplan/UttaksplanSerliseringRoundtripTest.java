package no.nav.foreldrepenger.common.innsyn.uttaksplan;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.MorsAktivitet;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Oppholdsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class UttaksplanSerliseringRoundtripTest extends SerializationTestBase {

    @Test
    void uttaksperiodeRoundtripSeraliseringsTest() {
        var uttaksperiode = getUttaksPeriode();
        test(uttaksperiode);
        assertThat(uttaksperiode.periode()).isNotNull();
        assertThat(uttaksperiode.periode().fom()).isNotNull();
        assertThat(uttaksperiode.periode().tom()).isNotNull();
    }

    @Test
    void uttaksplanUtenPerioderRoundtripSeraliseringTest() {
        var grunnlag = getGrunnlag();
        var uttaksplanUtenPerioder = new UttaksplanDto(grunnlag, List.of());
        test(uttaksplanUtenPerioder);
    }

    @Test
    void uttaksplanMedPerioderRoundtripSeraliseringTest() {
        var uttaksperiode = getUttaksPeriode();
        var grunnlag = getGrunnlag();
        var uttaksplanUtenPerioder = new UttaksplanDto(grunnlag, List.of(uttaksperiode, uttaksperiode));
        test(uttaksplanUtenPerioder, true);
    }

    private SøknadsGrunnlagDto getGrunnlag() {
        return new SøknadsGrunnlagDto(
                null,
                LocalDate.now(),
                null,
                Dekningsgrad.HUNDRE,
                1,
                true,
                true,
                true,
                null,
                false,
                true,
                false,
                true,
                false);
    }

    private UttaksPeriodeDto getUttaksPeriode() {
        return new UttaksPeriodeDto(
                Oppholdsårsak.UTTAK_FEDREKVOTE_ANNEN_FORELDER,
                Overføringsårsak.ALENEOMSORG,
                GraderingAvslagÅrsak.AVSLAG_PGA_100_PROSENT_ARBEID,
                UtsettelsePeriodeType.ARBEID,
                PeriodeResultatType.AVSLÅTT,
                true,
                true,
                LocalDate.now().minusMonths(4),
                LocalDate.now(),
                null,
                StønadskontoType.FELLESPERIODE,
                50.0,
                100,
                100,
                true,
                MorsAktivitet.UFØRE,
                true,
                true,
                0,
                UttakArbeidType.FRILANS,
                new ArbeidsgiverInfoDto("123", "Privat Arbeidsgiver", ArbeidsgiverType.PRIVAT),
                "ÅRSAK");
    }
}
