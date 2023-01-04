package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

import no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.Engangsstønad;
import no.nav.vedtak.felles.xml.soeknad.v3.Soeknad;

public final class ESV3JAXBUtil extends AbstractJAXBUtil {

    public ESV3JAXBUtil() {
        this(false);
    }

    public ESV3JAXBUtil(boolean validateMarshalling) {
        super(contextFra(Soeknad.class, Engangsstønad.class),
                validateMarshalling,
                "xsd/soeknad-v3/xsd/engangsstoenad/engangsstoenad-v3.xsd");
    }
}
