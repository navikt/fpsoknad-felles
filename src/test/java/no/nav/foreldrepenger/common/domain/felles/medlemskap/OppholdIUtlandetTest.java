package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;

class OppholdIUtlandetTest {

    @Test
    void oppholdForrige12MndSkalGiTrueMensNesteIkke() {
        var oppholdIUtlandet = new OppholdIUtlandet(List.of(
                utenlandsopphold(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(2)),
                utenlandsopphold(LocalDate.now().minusMonths(15), LocalDate.now().minusMonths(9))
        ));

        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().minusMonths(1))).isEqualByComparingTo(CountryCode.NO);
    }

    @Test
    void oppholdSomStarterIFortidOgEnderOppIFremtidSkalVæreOppholdIUtlandenNesteOgSiste12Mnd() {
        var oppholdIUtlandet = new OppholdIUtlandet(List.of(
                utenlandsopphold(LocalDate.now().minusMonths(5), LocalDate.now().plusMonths(5))
        ));

        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now())).isEqualByComparingTo(CountryCode.AT);
    }

    @Test
    void landVedDatoSkalVæreInklusiv() {
        var oppholdIUtlandet = new OppholdIUtlandet(List.of(
                utenlandsopphold(CountryCode.FI, LocalDate.now(), LocalDate.now().plusMonths(4)),
                utenlandsopphold(CountryCode.XK, LocalDate.now().plusMonths(5), LocalDate.now().plusMonths(9))
        ));

        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now())).isEqualByComparingTo(CountryCode.FI);
        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().plusMonths(2))).isEqualByComparingTo(CountryCode.FI);
        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().plusMonths(5))).isEqualByComparingTo(CountryCode.XK);
        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().plusMonths(9))).isEqualByComparingTo(CountryCode.XK);
        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().plusMonths(10))).isEqualByComparingTo(CountryCode.NO);
    }

    @Test
    void hvisDatoIkkeMatcherOppholdsperioderIUtlandetReturnerNO() {
        var oppholdIUtlandet = new OppholdIUtlandet(List.of(
                utenlandsopphold(CountryCode.FI, LocalDate.now(), LocalDate.now().plusMonths(4)),
                utenlandsopphold(CountryCode.XK, LocalDate.now().plusMonths(5), LocalDate.now().plusMonths(9))
        ));

        assertThat(oppholdIUtlandet.landVedDato(LocalDate.now().minusMonths(2))).isEqualByComparingTo(CountryCode.NO);
    }
    public static Utenlandsopphold utenlandsopphold(LocalDate fom, LocalDate tom) {
        return utenlandsopphold(CountryCode.AT, fom, tom);
    }

    public static Utenlandsopphold utenlandsopphold(CountryCode land, LocalDate fom, LocalDate tom) {
        return new Utenlandsopphold(land, new LukketPeriode(fom, tom));
    }
}
