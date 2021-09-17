package no.nav.foreldrepenger.common.util.jaxb;

import no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v1.Endringssoeknad;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v1.Foreldrepenger;
import no.nav.vedtak.felles.xml.soeknad.v1.Soeknad;

public final class FPV1JAXBUtil extends AbstractJAXBUtil {

    public FPV1JAXBUtil() {
        this(false);
    }

    public FPV1JAXBUtil(boolean validate) {
        this(validate, validate);
    }

    public FPV1JAXBUtil(boolean validateMarshalling, boolean validateUnmarshalling) {
        super(contextFra(
                Endringssoeknad.class,
                Foreldrepenger.class,
                no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v1.ObjectFactory.class,
                no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v1.ObjectFactory.class,
                Soeknad.class),
                validateMarshalling, validateUnmarshalling,
                "/soeknad-v1/xsd/foreldrepenger/foreldrepenger-v1.xsd",
                "/soeknad-v1/xsd/endringssoeknad/endringssoeknad-v1.xsd",
                "/soeknad-v1/xsd/soeknad-v1.xsd");
    }
}
