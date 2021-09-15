package no.nav.foreldrepenger.common.innsyn.vedtak;

import no.nav.foreldrepenger.common.domain.FagsakType;
import no.nav.foreldrepenger.common.innsyn.SøknadEgenskap;

public record VedtakMetadata(String journalpostId, SøknadEgenskap e) {
    public FagsakType getType() {
        return e.getFagsakType();
    }

    public String getVersjon() {
        return e.getVersjon().name();
    }
}
