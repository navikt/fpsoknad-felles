package no.nav.foreldrepenger.common.domain.foreldrepenger;

import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Oppholdsårsak.UTTAK_FEDREKVOTE_ANNEN_FORELDER;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak.IKKE_RETT_ANNEN_FORELDER;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType.FEDREKVOTE;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UtsettelsesÅrsak.INSTITUSJONSOPPHOLD_BARNET;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.ukeDagNær;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.uttaksPeriode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestFordeling {

    @Test
    void testFordelingUttakFørstErUtsettelse() {

        var utsettelseStart = ukeDagNær(LocalDate.now().plusMonths(2));
        var f = new Fordeling(true, List.of(
                uttaksPeriode(ukeDagNær(LocalDate.now().plusMonths(3)), ukeDagNær(LocalDate.now().plusMonths(4))),
                new OppholdsPeriode(ukeDagNær(LocalDate.now().plusMonths(1)), ukeDagNær(utsettelseStart),
                        UTTAK_FEDREKVOTE_ANNEN_FORELDER, null),
                new UtsettelsesPeriode(ukeDagNær(utsettelseStart),
                        ukeDagNær(LocalDate.now().plusMonths(3)), true,INSTITUSJONSOPPHOLD_BARNET, null, null)),
                false);
        assertEquals(utsettelseStart, f.getFørsteUttaksdag());
    }

    @Test
    void testFordelingUttakFørstErUttak() {

        var uttakStart = ukeDagNær(LocalDate.now().minusMonths(2));
        var f = new Fordeling(true, List.of(
                new OppholdsPeriode(ukeDagNær(LocalDate.now().plusMonths(1)), ukeDagNær(LocalDate.now().plusMonths(2)),
                        UTTAK_FEDREKVOTE_ANNEN_FORELDER, null),
                new OverføringsPeriode(ukeDagNær(LocalDate.now()), ukeDagNær(LocalDate.now().plusMonths(1)),
                        Overføringsårsak.ALENEOMSORG, FEDREKVOTE, null),
                new UtsettelsesPeriode(ukeDagNær(LocalDate.now().plusMonths(2)),
                        ukeDagNær(LocalDate.now().plusMonths(3)), true, INSTITUSJONSOPPHOLD_BARNET, null, null),
                uttaksPeriode(ukeDagNær(uttakStart), ukeDagNær(LocalDate.now().plusMonths(4)))),
                false);
        assertEquals(uttakStart, f.getFørsteUttaksdag());
    }

    @Test
    void testOverføringsperiodeErUttak() {

        var uttaksStart = ukeDagNær(LocalDate.now().minusMonths(1));
        var f = new Fordeling(true, List.of(
                new OverføringsPeriode(uttaksStart, ukeDagNær(LocalDate.now().plusMonths(2)),
                        IKKE_RETT_ANNEN_FORELDER, FEDREKVOTE, null)),
                false);
        assertEquals(uttaksStart, f.getFørsteUttaksdag());
    }

    @Test
    void testIngenFørsteDag() {

        var f = new Fordeling(true, List.of(
                new OppholdsPeriode(ukeDagNær(LocalDate.now()), ukeDagNær(LocalDate.now().plusMonths(1)),
                        UTTAK_FEDREKVOTE_ANNEN_FORELDER, null)),
                false);
        assertNull(f.getFørsteUttaksdag());
    }

    @Test
    void testFørsteUttaksdagIgnorererFriPerioder() {
        var friFom = ukeDagNær(LocalDate.now().minusDays(10));
        var friTom = friFom.plusDays(2);
        var ordinærUtsettelseFom = friTom.plusDays(1);
        var ordinærUtsettelseTom = ordinærUtsettelseFom.plusDays(1);
        var fordeling = new Fordeling(true, List.of(
                new UtsettelsesPeriode(friFom, friTom, true, UtsettelsesÅrsak.FRI, MorsAktivitet.ARBEID_OG_UTDANNING, List.of()),
                new UtsettelsesPeriode(ordinærUtsettelseFom, ordinærUtsettelseTom, true, INSTITUSJONSOPPHOLD_BARNET, MorsAktivitet.INNLAGT, List.of())),
                false);
        assertEquals(ordinærUtsettelseFom, fordeling.getFørsteUttaksdag());
    }

}
