package no.nav.foreldrepenger.common.error;

import no.nav.foreldrepenger.common.innsending.SøknadType;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.util.Versjon;

public abstract class SøknadEgenskapException extends RuntimeException {
    private final SøknadEgenskap egenskap;

    protected SøknadEgenskapException(SøknadEgenskap egenskap) {
        this(null, egenskap, null);
    }

    protected SøknadEgenskapException(String msg, SøknadEgenskap egenskap, Throwable cause) {
        super(msg, cause);
        this.egenskap = egenskap;
    }

    public SøknadType getType() {
        return egenskap.getType();
    }

    public Versjon getVersjon() {
        return egenskap.getVersjon();
    }
}
