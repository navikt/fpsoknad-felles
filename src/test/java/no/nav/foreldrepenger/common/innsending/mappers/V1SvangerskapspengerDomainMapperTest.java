package no.nav.foreldrepenger.common.innsending.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils;

class V1SvangerskapspengerDomainMapperTest {

    @Test
    void sjekkAvtaltFerieMapping() {
        var søknad = ForeldrepengerTestUtils.svp();
        var svp = (no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger) søknad.getYtelse();
        var svpXMLModell = V1SvangerskapspengerDomainMapper.tilSvangerskapspenger(svp);
        assertThat(svpXMLModell.getAvtaltFerieListe())
                .isNotNull()
                .matches(afl -> afl.getAvtaltFerie().size() == 1)
                .satisfies(afl -> {
                    var avtaltFerie = afl.getAvtaltFerie().getFirst();
                    var forventetFom = LocalDate.now().plusDays(10);
                    var forventetTom = LocalDate.now().plusDays(20);
                    assertThat(avtaltFerie.getAvtaltFerieFom()).isEqualTo(forventetFom);
                    assertThat(avtaltFerie.getAvtaltFerieTom()).isEqualTo(forventetTom);
                    assertThat(avtaltFerie.getArbeidsgiver().getIdentifikator()).isEqualTo(Orgnummer.MAGIC_ORG.value());
                });
    }

}
