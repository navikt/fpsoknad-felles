package no.nav.foreldrepenger.common.domain.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Saksnummer;

class SaksnummerValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void happyCaseTest() {
        assertTrue(validator.validate(new Saksnummer("1234142141241")).isEmpty());
    }

    @Test
    void valideringsFeilVedBokstaver() {
        assertFalse(validator.validate(new Saksnummer("ABCDE12314")).isEmpty());
        assertFalse(validator.validate(new Saksnummer("1234,23123")).isEmpty());
        assertFalse(validator.validate(new Saksnummer("1234.23123")).isEmpty());
        assertFalse(validator.validate(new Saksnummer("1234 23 123")).isEmpty());
    }

}
