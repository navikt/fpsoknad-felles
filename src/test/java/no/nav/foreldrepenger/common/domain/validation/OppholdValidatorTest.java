package no.nav.foreldrepenger.common.domain.validation;


import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;

class OppholdValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void happyCase() {
        var tidligereOppholdMedOverlapp = List.of(
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1)))
        );
        var fremtidigOppholdMedOverlapp = List.of(
                new Utenlandsopphold(CountryCode.NA, new LukketPeriode(now().plusMonths(2), now().plusMonths(3).minusDays(1)))
        );
        var medlemsskap = new Medlemsskap(tidligereOppholdMedOverlapp, fremtidigOppholdMedOverlapp);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void toPerioderMedFomOgTomSattTilSammeDatoSkalIkkeGiValideringsfeilPgaOverlapp() {
        var tidligereOppholdMedOverlapp = List.of(
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1))),
                new Utenlandsopphold(CountryCode.NA, new LukketPeriode(now().minusMonths(6).minusDays(1), now().minusMonths(3).minusDays(1)))
                );
        var medlemsskap = new Medlemsskap(tidligereOppholdMedOverlapp, null);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void toPerioderMedOverlappSkalGiValideringsfeil() {
        var fremtidigOppholdMedOverlapp = List.of(
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(now().plusMonths(1), now().plusYears(1).minusDays(1))),
                new Utenlandsopphold(CountryCode.NA, new LukketPeriode(now().plusMonths(2), now().plusMonths(3).minusDays(1)))
                );
        var medlemsskap = new Medlemsskap(null, fremtidigOppholdMedOverlapp);
        assertThat(validertOK(medlemsskap)).isFalse();
    }


    @Test
    void tidligereOppholdMedPeriodeSomErFramITidErIkkeLov() {
        var tidligereOpphold = List.of(
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1))),
                new Utenlandsopphold(CountryCode.NA, new LukketPeriode(now().minusMonths(10), now().minusMonths(3).minusDays(1))),
                new Utenlandsopphold(CountryCode.NA, new LukketPeriode(now().minusMonths(5), now().plusWeeks(1).minusDays(1)))
        );
        var medlemsskap = new Medlemsskap(tidligereOpphold, null);
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    @Test
    void fremtidigOppholdMedPeriodeSomErTilbakeITidErIkkeLov() {
        var fremtidigOppholdMedOverlapp = List.of(
                new Utenlandsopphold(CountryCode.DE, new LukketPeriode(now().minusMonths(4), now().minusMonths(1).minusDays(1))),
                new Utenlandsopphold(CountryCode.DE, new LukketPeriode(now(), now().plusMonths(1).minusDays(1))),
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(now().plusMonths(1), now().plusYears(1).minusDays(1)))
        );
        var medlemsskap = new Medlemsskap(null, fremtidigOppholdMedOverlapp);
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    private boolean validertOK(Medlemsskap medlemsskap) {
        return validator.validate(medlemsskap).isEmpty();
    }
}
