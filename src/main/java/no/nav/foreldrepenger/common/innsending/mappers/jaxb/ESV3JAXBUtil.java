package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

import no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.Engangsstønad;
import no.nav.vedtak.felles.xml.soeknad.v3.Soeknad;

public final class ESV3JAXBUtil extends AbstractJAXBUtil {

    public ESV3JAXBUtil() {
        super(contextFra(Soeknad.class, Engangsstønad.class));
    }
}
