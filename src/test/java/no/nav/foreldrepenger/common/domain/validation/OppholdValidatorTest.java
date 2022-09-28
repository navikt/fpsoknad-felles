package no.nav.foreldrepenger.common.domain.validation;


import static no.nav.foreldrepenger.common.domain.validation.OppholdValidator.erOverlappende;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    void overlappSammeDag() {
        boolean overlapp = erOverlappende(
                new Utenlandsopphold(CountryCode.NO, new LukketPeriode(LocalDate.of(2022, 6, 20), LocalDate.of(2022, 6, 26))),
                new Utenlandsopphold(CountryCode.SE, new LukketPeriode(LocalDate.of(2022, 6, 25), LocalDate.of(2022, 6, 27))));
        assertThat(overlapp).isFalse();
    }
    @Test
    void happycaseIngenPeridoer() {
        var medlemsskap = new Medlemsskap(null, null);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void happycaseEnPeriode() {
        var periode1 = new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1));
        var medlemsskap = new Medlemsskap(opphold(periode1), null);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void happyCaseFortidOgFremtid() {
        var periode1 = new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1));
        var periode2 = new LukketPeriode(now().minusMonths(6), now());
        var fortid = opphold(periode2, periode1);

        var periode3 = new LukketPeriode(now(), now().plusMonths(6));
        var periode4 = new LukketPeriode(now().plusMonths(6).plusDays(1), now().plusYears(1));
        var framtidig = opphold(periode3, periode4);
        var medlemsskap = new Medlemsskap(fortid, framtidig);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void toPerioderMedFomOgTomSattTilSammeDatoSkalIkkeGiValideringsfeilPgaOverlapp() {
        var overlappendeDato = now().minusMonths(6).minusDays(1);
        var periode1 = new LukketPeriode(now().minusYears(1), overlappendeDato);
        var periode2 = new LukketPeriode(overlappendeDato, now().minusMonths(3).minusDays(1));
        var medlemsskap = new Medlemsskap(opphold(periode1, periode2), null);
        assertThat(validertOK(medlemsskap)).isTrue();
    }

    @Test
    void toPerioderMedOverlappIFramtidSkalGiValideringsfeil() {
        var periode1 = new LukketPeriode(now().plusMonths(1), now().plusYears(1).minusDays(1));
        var periode2 = new LukketPeriode(now().plusMonths(2), now().plusMonths(3).minusDays(1));
        var medlemsskap = new Medlemsskap(null, opphold(periode1, periode2));
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    @Test
    void toPerioderMedOverlappIFortidSkalGiValideringsfeil() {
        var periode1 = new LukketPeriode(now().plusMonths(1), now().plusYears(1).minusDays(1));
        var periode2 = new LukketPeriode(now().plusMonths(2), now().plusMonths(3).minusDays(1));
        var medlemsskap = new Medlemsskap(opphold(periode1, periode2), null);
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    @Test
    void tidligereOppholdMedPeriodeSomErFramITidErIkkeLov() {
        var periode1 = new LukketPeriode(now().minusYears(1), now().minusMonths(6).minusDays(1));
        var periode2 = new LukketPeriode(now().minusMonths(10), now().minusMonths(3).minusDays(1));
        var periode3 = new LukketPeriode(now().minusMonths(5), now().plusWeeks(1).minusDays(1));
        var medlemsskap = new Medlemsskap(opphold(periode1, periode2, periode3), null);
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    @Test
    void fremtidigOppholdMedPeriodeSomErTilbakeITidErIkkeLov() {
        var periode1 = new LukketPeriode(now().minusMonths(4), now().minusMonths(1).minusDays(1));
        var periode2 = new LukketPeriode(now(), now().plusMonths(1).minusDays(1));
        var periode3 = new LukketPeriode(now().plusMonths(1), now().plusYears(1).minusDays(1));
        var medlemsskap = new Medlemsskap(null, opphold(periode1, periode2, periode3));
        assertThat(validertOK(medlemsskap)).isFalse();
    }

    private static LocalDate now() {
        return LocalDate.now();
    }

    private static List<Utenlandsopphold> opphold(LukketPeriode... perioder) {
        return Arrays.stream(perioder)
                .map(s -> new Utenlandsopphold(CountryCode.SE, s))
                .collect(Collectors.toList());
    }

    private boolean validertOK(Medlemsskap medlemsskap) {
        return validator.validate(medlemsskap).isEmpty();
    }
}
