package no.nav.foreldrepenger.common.domain.validation;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Collections;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.uttaksPeriode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeriodeValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testStartStoppFeil() {
        assertFalse(validator.validate(new LukketPeriode(now().plusMonths(6), now())).isEmpty());
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
        var fordeling = new Fordeling(true, Collections.singletonList(uttaksPeriode(null, null)), null);
        assertFalse(validator.validate(fordeling).isEmpty());
//        var es = new Endringssøknad(LocalDate.now(), new Søker(BrukerRolle.MOR, Målform.standard()), fordeling, null, null,
//                null, Saksnummer.valueOf("42"));
//        assertFalse(validator.validate(es).isEmpty());
    }

    private static LocalDate now() {
        return LocalDate.now();
    }

}
