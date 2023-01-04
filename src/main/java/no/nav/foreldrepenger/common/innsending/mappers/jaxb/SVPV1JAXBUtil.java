package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

public final class SVPV1JAXBUtil extends AbstractJAXBUtil {

    public SVPV1JAXBUtil() {
        this(false);
    }

    public SVPV1JAXBUtil(boolean validateMarshalling) {
        super(contextFra(
                no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Svangerskapspenger.class,
                no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.ObjectFactory.class,
                no.nav.vedtak.felles.xml.soeknad.v3.Soeknad.class),
                validateMarshalling,
                "xsd/soeknad-v3/xsd/svangerskapspenger/svangerskapspenger-v1.xsd",
                "xsd/soeknad-v3/xsd/soeknad-v3.xsd");
    }

}
