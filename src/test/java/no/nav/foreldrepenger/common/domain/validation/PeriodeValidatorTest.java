package no.nav.foreldrepenger.common.domain.validation;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.uttaksPeriode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.BrukerRolle;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

class PeriodeValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testIkkeOverlappendeFortid() {
        var periode1 = new LukketPeriode(now().minusMonths(6), now());
        var periode2 = new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1));
        var medlemsskap = new Medlemsskap(opphold(periode2, periode1), null);
        assertTrue(validator.validate(medlemsskap).isEmpty());
    }

    @Test
    void testIkkeOverlappendeFramtidig() {
        var periode1 = new LukketPeriode(now(), now().plusMonths(6));
        var periode2 = new LukketPeriode(now().plusMonths(6).plusDays(1), now().plusYears(1));
        var framtidig = opphold(periode2, periode1);
        var medlemsskap = new Medlemsskap(null, framtidig);
        assertTrue(validator.validate(medlemsskap).isEmpty());
    }

    @Test
    void testIkkeOverlappendeMenFortid() {
        var periode1 = new LukketPeriode(now().minusMonths(6), now());
        var periode2 = new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1));
        var framtidig = opphold(periode2, periode1);
        var medlemsskap = new Medlemsskap(null, framtidig);
        assertFalse(validator.validate(medlemsskap).isEmpty());
    }

    @Test
    void testStartStoppFeil() {
        assertFalse(validator.validate(new LukketPeriode(now().plusMonths(6), now())).isEmpty());
    }

    @Test
    void testIkkeOverlappendeMenFramtid() {
        var periode1 = new LukketPeriode(now(), now().plusMonths(6));
        var periode2 = new LukketPeriode(now().plusMonths(6).plusDays(1), now().plusYears(1));
        var framtidig = opphold(periode2, periode1);
        var medlemsskap = new Medlemsskap(framtidig, null);
        assertFalse(validator.validate(medlemsskap).isEmpty());
    }

    @Test
    void testLukketPeriodeMedVedleggOK() {
        var periode1 = uttaksPeriode(now(), now().plusMonths(6));
        assertTrue(validator.validate(periode1).isEmpty());
    }

    @Test
    void testLukketPeriodeMedVedleggNull() {
        var periode1 = uttaksPeriode(now(), null);
        assertFalse(validator.validate(periode1).isEmpty());
    }

    @Test
    void testLukketPeriodeMedVedleggFomNull() {
        var periode1 = uttaksPeriode(null, now());
        assertFalse(validator.validate(periode1).isEmpty());
    }

    @Test
    void testLukketPeriodeMedVedleggFomFørTom() {
        assertFalse(validator.validate(uttaksPeriode(now(), now().minusDays(1))).isEmpty());
    }

    @Test
    void testLukketPeriodeMedVedleggFomOgTomLike() {
        assertTrue(validator.validate(uttaksPeriode(now(), now())).isEmpty());
    }



    @Test
    void testFordeling() {
        var fordeling = new Fordeling(true, Collections.singletonList(uttaksPeriode(null, null)));
        assertFalse(validator.validate(fordeling).isEmpty());
        var es = new Endringssøknad(LocalDate.now(), new Søker(BrukerRolle.MOR, Målform.standard()), fordeling, null, null,
                null, "42");
        assertFalse(validator.validate(es).isEmpty());
    }

    @Test
    void testOverlappendeFortid() {
        var periode1 = new LukketPeriode(now().minusMonths(6), now());
        var periode2 = new LukketPeriode(now().minusYears(1), now().minusMonths(4));
        var periode3 = new LukketPeriode(now().minusMonths(4), now());
        var tidligere = opphold(periode1, periode2, periode3);
        var medlemsskap = new Medlemsskap(tidligere, null);
        assertFalse(validator.validate(medlemsskap).isEmpty());
    }

    private static LocalDate now() {
        return LocalDate.now();
    }

    private static List<Utenlandsopphold> opphold(LukketPeriode... perioder) {
        return Arrays.stream(perioder)
                .map(s -> new Utenlandsopphold(CountryCode.SE, s))
                .collect(Collectors.toList());
    }
}
