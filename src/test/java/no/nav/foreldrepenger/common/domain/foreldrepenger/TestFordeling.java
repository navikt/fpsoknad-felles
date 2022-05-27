package no.nav.foreldrepenger.common.domain.foreldrepenger;

import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Oppholdsårsak.UTTAK_FEDREKVOTE_ANNEN_FORELDER;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak.IKKE_RETT_ANNEN_FORELDER;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType.FEDREKVOTE;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UtsettelsesÅrsak.INSTITUSJONSOPPHOLD_BARNET;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.ukeDagNær;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.uttaksPeriode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.OppholdsPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.OverføringsPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UtsettelsesPeriode;

class TestFordeling {

    @Test
    void testFordelingUttakFørstErUtsettelse() {

        var utsettelseStart = ukeDagNær(LocalDate.now().plusMonths(2));
        var f = new Fordeling(true, List.of(
                uttaksPeriode(ukeDagNær(LocalDate.now().plusMonths(3)), ukeDagNær(LocalDate.now().plusMonths(4))),
                new OppholdsPeriode(ukeDagNær(LocalDate.now().plusMonths(1)), ukeDagNær(utsettelseStart),
                        UTTAK_FEDREKVOTE_ANNEN_FORELDER, null),
                new UtsettelsesPeriode(ukeDagNær(utsettelseStart),
                        ukeDagNær(LocalDate.now().plusMonths(3)), true, Collections.singletonList("222"),
                        INSTITUSJONSOPPHOLD_BARNET, null, null)));
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
                        ukeDagNær(LocalDate.now().plusMonths(3)), true, Collections.singletonList("222"),
                        INSTITUSJONSOPPHOLD_BARNET, null, null),
                uttaksPeriode(ukeDagNær(uttakStart), ukeDagNær(LocalDate.now().plusMonths(4)))));
        assertEquals(uttakStart, f.getFørsteUttaksdag());
    }

    @Test
    void testOverføringsperiodeErUttak() {

        var uttaksStart = ukeDagNær(LocalDate.now().minusMonths(1));
        var f = new Fordeling(true, List.of(
                new OverføringsPeriode(uttaksStart, ukeDagNær(LocalDate.now().plusMonths(2)),
                        IKKE_RETT_ANNEN_FORELDER, FEDREKVOTE, null)));
        assertEquals(uttaksStart, f.getFørsteUttaksdag());
    }

    @Test
    void testIngenFørsteDag() {

        var f = new Fordeling(true, List.of(
                new OppholdsPeriode(ukeDagNær(LocalDate.now()), ukeDagNær(LocalDate.now().plusMonths(1)),
                        UTTAK_FEDREKVOTE_ANNEN_FORELDER, null)));
        assertNull(f.getFørsteUttaksdag());
    }

}
