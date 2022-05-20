package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

public final class FPV3JAXBUtil extends AbstractJAXBUtil {

    public FPV3JAXBUtil() {
        this(false);
    }

    public FPV3JAXBUtil(boolean validate) {
        this(validate, validate);
    }

    public FPV3JAXBUtil(boolean validateMarshalling, boolean validateUnmarshalling) {
        super(contextFra(
                no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v3.Endringssoeknad.class,
                no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Foreldrepenger.class,
                no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.ObjectFactory.class,
                no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v3.ObjectFactory.class,
                no.nav.vedtak.felles.xml.soeknad.v3.Soeknad.class),
                validateMarshalling, validateUnmarshalling,
                "xsd/soeknad-v3/xsd/foreldrepenger/foreldrepenger-v3.xsd",
                "xsd/soeknad-v3/xsd/endringssoeknad/endringssoeknad-v3.xsd",
                "xsd/soeknad-v3/xsd/soeknad-v3.xsd");
    }
}
