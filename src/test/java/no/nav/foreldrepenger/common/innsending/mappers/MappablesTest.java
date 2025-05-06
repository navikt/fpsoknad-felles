package no.nav.foreldrepenger.common.innsending.mappers;

import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MappablesTest {

    private List<DomainMapper> mappers;

    @BeforeEach
    void setUp() {
        mappers = List.of(
                new V1SvangerskapspengerDomainMapper(),
                new V3EngangsstønadDomainMapper(),
                new V3ForeldrepengerDomainMapper()
        );
    }

    @Test
    void sjekkerAtMapperEgenskaperGenererFireUlikeMapperEgenskaper() {
        var mapperEgenskaper = Mappables.egenskaperFor(mappers);
        assertThat(mapperEgenskaper.getEgenskaper())
                .containsExactlyInAnyOrder(
                        SøknadEgenskap.INITIELL_SVANGERSKAPSPENGER,
                        SøknadEgenskap.INITIELL_ENGANGSSTØNAD,
                        SøknadEgenskap.INITIELL_FORELDREPENGER,
                        SøknadEgenskap.ENDRING_FORELDREPENGER)
                .hasSize(4);
    }


    @Test
    void sjekkAtMapperHenterUtRiktigDomainMapperGittSøknadEgenskap() {
        assertThat(Mappables.mapperFor(mappers, SøknadEgenskap.INITIELL_SVANGERSKAPSPENGER)).isInstanceOf(V1SvangerskapspengerDomainMapper.class);
        assertThat(Mappables.mapperFor(mappers, SøknadEgenskap.INITIELL_ENGANGSSTØNAD)).isInstanceOf(V3EngangsstønadDomainMapper.class);
        assertThat(Mappables.mapperFor(mappers, SøknadEgenskap.INITIELL_FORELDREPENGER)).isInstanceOf(V3ForeldrepengerDomainMapper.class);
        assertThat(Mappables.mapperFor(mappers, SøknadEgenskap.ENDRING_FORELDREPENGER)).isInstanceOf(V3ForeldrepengerDomainMapper.class);
    }

}
