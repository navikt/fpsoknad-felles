package no.nav.foreldrepenger.common.innsending.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.oppslag.Oppslag;

class MappablesTest {

    private List<DomainMapper> mappers;

    @BeforeEach
    void setUp() {
        var oppslag = mock(Oppslag.class);
        mappers = List.of(
                new V1SvangerskapspengerDomainMapper(),
                new V3EngangsstønadDomainMapper(oppslag),
                new V3ForeldrepengerDomainMapper(oppslag)
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
