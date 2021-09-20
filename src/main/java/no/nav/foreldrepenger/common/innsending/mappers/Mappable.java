package no.nav.foreldrepenger.common.innsending.mappers;

import no.nav.foreldrepenger.common.innsyn.SøknadEgenskap;

public interface Mappable {

    MapperEgenskaper mapperEgenskaper();

    default boolean kanMappe(SøknadEgenskap egenskap) {
        return mapperEgenskaper().kanMappe(egenskap);
    }
}
