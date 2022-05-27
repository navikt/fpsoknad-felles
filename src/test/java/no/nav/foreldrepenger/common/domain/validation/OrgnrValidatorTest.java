package no.nav.foreldrepenger.common.domain.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Orgnummer;

class OrgnrValidatorTest {

    private static final String NAV = "999263550";
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testOK() {
        assertTrue(validator.validate(new Orgnummer(NAV)).isEmpty());
    }

    @Test
    void testOKbutWrongFirstDigit() {
        assertFalse(validator.validate(new Orgnummer("123456785")).isEmpty());
    }

    @Test
    void testStrange() {
        assertTrue(validator.validate(new Orgnummer("999999999")).isEmpty());
    }

    @Test
    void testNull() {
        assertTrue(validator.validate(new Orgnummer(null)).isEmpty());
    }

    @Test
    void testLength() {
        assertFalse(validator.validate(new Orgnummer("666")).isEmpty());
    }
}
