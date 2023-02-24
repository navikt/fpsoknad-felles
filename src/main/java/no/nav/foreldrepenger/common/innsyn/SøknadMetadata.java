package no.nav.foreldrepenger.common.innsyn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.nav.foreldrepenger.common.innsending.SøknadType;
import no.nav.foreldrepenger.common.util.Versjon;


public record SøknadMetadata(SøknadEgenskap egenskaper, String journalpostId) {

    @JsonIgnore
    public Versjon getVersjon() {
        return egenskaper.getVersjon();
    }

    @JsonIgnore
    public SøknadType getType() {
        return egenskaper.getType();
    }
}
