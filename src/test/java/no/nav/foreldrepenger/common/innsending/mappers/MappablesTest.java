package no.nav.foreldrepenger.common.innsending.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;

class MappablesTest {

    private List<DomainMapper> mappers;

    @BeforeEach
    void setUp() {
        mappers = List.of(
                new V1SvangerskapspengerDomainMapper(),
                new V3EngangsstønadDomainMapper(fnr -> null),
                new V3ForeldrepengerDomainMapper(fnr -> null)
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
