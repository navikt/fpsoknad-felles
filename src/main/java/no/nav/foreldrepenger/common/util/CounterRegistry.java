package no.nav.foreldrepenger.common.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

public final class CounterRegistry {
    private static final String FELLES = "felles";
    private static final String SUKSESS = "suksess";
    private static final String FEILET = "feilet";
    private static final String TYPE = "type";
    private static final String YTELSE = "ytelse";
    private static final String FORELDREPENGER = "foreldrepenger";
    private static final String ENGANGSSTØNAD = "engangsstønad";
    private static final String SVANGERSKAPSPENGER = "svangerskapspenger";
    private static final String FPINFO_KVITTERINGER = "fpinfo.kvitteringer";
    private static final String FPFORDEL_KVITTERINGER = "fpfordel.kvitteringer";
    private static final String FPFORDEL_SEND = "fpfordel.send";
    private static final String VARSEL_SEND = "varsel.send";
    public static final Counter ES_FØRSTEGANG = esCounter(FPFORDEL_SEND, "INITIELL_ENGANGSSTØNAD");
    public static final Counter ES_ETTERSENDING = esCounter(FPFORDEL_SEND, "ETTERSENDING_ENGANGSSTØNAD");
    public static final Counter FP_SENDFEIL = fpCounter(FPFORDEL_SEND, "feil");
    public static final Counter FP_FØRSTEGANG = fpCounter(FPFORDEL_SEND, "INITIELL_FORELDREPENGER");
    public static final Counter FP_ENDRING = fpCounter(FPFORDEL_SEND, "ENDRING_FORELDREPENGER");
    public static final Counter FP_ETTERSENDING = fpCounter(FPFORDEL_SEND, "ETTERSENDING_FORELDREPENGER");
    public static final Counter SVP_FØRSTEGANG = svpCounter(FPFORDEL_SEND, "INITIELL_SVANGERSKAPSPENGER");
    public static final Counter SVP_ETTERSENDING = svpCounter(FPFORDEL_SEND, "ETTERSENDING_SVANGERSKAPSPENGER");
    public static final Counter GITTOPP_KVITTERING = fpCounter(FPFORDEL_KVITTERINGER, "gittopp");
    public static final Counter MANUELL_KVITTERING = fpCounter(FPFORDEL_KVITTERINGER, "gosys");
    public static final Counter FORDELT_KVITTERING = fpCounter(FPFORDEL_KVITTERINGER, "fordelt");
    public static final Counter FEILET_KVITTERINGER = fpCounter(FPFORDEL_KVITTERINGER, FEILET);
    public static final Counter PENDING = fpCounter(FPINFO_KVITTERINGER, "påvent");
    public static final Counter REJECTED = fpCounter(FPINFO_KVITTERINGER, "avslått");
    public static final Counter ACCEPTED = fpCounter(FPINFO_KVITTERINGER, "innvilget");
    public static final Counter RUNNING = fpCounter(FPINFO_KVITTERINGER, "pågår");
    public static final Counter FAILED = fpCounter(FPINFO_KVITTERINGER, FEILET);
    public static final Counter VARSEL_FAILED = counter(VARSEL_SEND, FELLES, FEILET);
    public static final Counter VARSEL_SUCCESS = counter(VARSEL_SEND, FELLES, SUKSESS);

    private CounterRegistry() {
    }

    private static Counter fpCounter(String name, String type) {
        return counter(name, FORELDREPENGER, type);
    }

    private static Counter svpCounter(String name, String type) {
        return counter(name, SVANGERSKAPSPENGER, type);
    }

    private static Counter esCounter(String name, String type) {
        return counter(name, ENGANGSSTØNAD, type);
    }

    private static Counter counter(String name, String ytelse, String type) {
        return Metrics.counter(name, YTELSE, ytelse, TYPE, type);
    }
}
