package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

public final class SVPV1JAXBUtil extends AbstractJAXBUtil {

    public SVPV1JAXBUtil() {
        this(false);
    }

    public SVPV1JAXBUtil(boolean validate) {
        this(validate, validate);
    }

    public SVPV1JAXBUtil(boolean validateMarshalling, boolean validateUnmarshalling) {
        super(contextFra(
                no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Svangerskapspenger.class,
                no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.ObjectFactory.class,
                no.nav.vedtak.felles.xml.soeknad.v3.Soeknad.class),
                validateMarshalling, validateUnmarshalling,
                "xsd/soeknad-v3/xsd/svangerskapspenger/svangerskapspenger-v1.xsd",
                "xsd/soeknad-v3/xsd/soeknad-v3.xsd");
    }

}
