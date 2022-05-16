package no.nav.foreldrepenger.common.util.jaxb;

import no.seres.xsd.nav.inntektsmelding_m._20181211.InntektsmeldingM;

public final class InntektsmeldingJAXBUtil extends AbstractJAXBUtil {

    public InntektsmeldingJAXBUtil() {
        this(false, false);
    }

    public InntektsmeldingJAXBUtil(boolean validateMarshalling, boolean validateUnmarshalling) {
        super(contextFra(InntektsmeldingM.class),
                validateMarshalling, validateUnmarshalling,
                "xsd/inntektsmelding-v1/xsd/Inntektsmelding20181211_V7.xsd",
                "xsd/inntektsmelding-v1/xsd/Inntektsmelding_kodelister_20180924.xsd");
    }
}
