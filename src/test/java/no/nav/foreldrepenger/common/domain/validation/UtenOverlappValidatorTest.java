package no.nav.foreldrepenger.common.domain.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.OppholdIUtlandet;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;

class UtenOverlappValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void overlappendePerioderSkalFeile() {
        var opphold = new OppholdIUtlandet(List.of(
                utenlandsopphold(LocalDate.now(), LocalDate.now().plusMonths(2)),
                utenlandsopphold(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(4))
        ));
        assertThat(validertOK(opphold)).isFalse();
    }

    @Test
    void toPerioderMedFomOgTomSattTilSammeDatoSkalIkkeGiValideringsfeilPgaOverlapp() {
        var opphold = new OppholdIUtlandet(List.of(
                utenlandsopphold(LocalDate.now(), LocalDate.now().plusMonths(2)),
                utenlandsopphold(LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(4))
        ));
        assertThat(validertOK(opphold)).isTrue();
    }

    @Test
    void toPerioderSomIkkeOverlapperSkalValiderstilOK() {
        var opphold = new OppholdIUtlandet(List.of(
                utenlandsopphold(LocalDate.now(), LocalDate.now().plusMonths(2)),
                utenlandsopphold(LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(4))
        ));
        assertThat(validertOK(opphold)).isTrue();
    }

    @Test
    void valideringFeilerNårFomErEtterTom() {
        var utenlandsopphold = utenlandsopphold(LocalDate.now(), LocalDate.now().minusMonths(1));
        assertThat(validertOK(utenlandsopphold)).isFalse();
    }

    @Test
    void valideringFeilerNårFomErLikTom() {
        var utenlandsopphold = utenlandsopphold(LocalDate.now(), LocalDate.now());
        assertThat(validertOK(utenlandsopphold)).isFalse();
    }

    @Test
    void valideringFeilerIkkeForUtenladnsoppholdNårFomErFørTom() {
        var utenlandsopphold = utenlandsopphold(LocalDate.now(), LocalDate.now().plusMonths(2));
        assertThat(validertOK(utenlandsopphold)).isTrue();
    }


    public static Utenlandsopphold utenlandsopphold(LocalDate fom, LocalDate tom) {
        return utenlandsopphold(CountryCode.AT, fom, tom);
    }

    public static Utenlandsopphold utenlandsopphold(CountryCode land, LocalDate fom, LocalDate tom) {
        return new Utenlandsopphold(land, new LukketPeriode(fom, tom));
    }

    private boolean validertOK(Object objekt) {
        return validator.validate(objekt).isEmpty();
    }
}
