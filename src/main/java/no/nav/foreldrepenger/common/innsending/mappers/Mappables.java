package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nav.foreldrepenger.common.error.UnsupportedEgenskapException;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;

public final class Mappables {
    public static final String DELEGERENDE = "delegerende";
    private static final Logger LOG = LoggerFactory.getLogger(Mappables.class);

    private Mappables() {
    }

    public static <T extends Mappable> MapperEgenskaper egenskaperFor(List<T> mappables) {
        return new MapperEgenskaper(safeStream(mappables)
                .map(Mappable::mapperEgenskaper)
                .map(MapperEgenskaper::getEgenskaper)
                .flatMap(Collection::stream)
                .toList());
    }

    public static <T extends Mappable> T mapperFor(List<T> mappables, SøknadEgenskap egenskap) {
        T mapper = safeStream(mappables)
                .filter(m -> m.kanMappe(egenskap))
                .findFirst()
                .orElseThrow(() -> new UnsupportedEgenskapException(mappables, egenskap));
        LOG.trace("Bruker mapper {} for {}", mapper.getClass().getSimpleName(), egenskap);
        return mapper;
    }

}
