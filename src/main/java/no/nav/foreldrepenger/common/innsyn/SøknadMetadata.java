package no.nav.foreldrepenger.common.innsyn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import no.nav.foreldrepenger.common.innsending.SøknadType;
import no.nav.foreldrepenger.common.util.Versjon;

@Data
public class SøknadMetadata {

    private final SøknadEgenskap egenskaper;
    private final String journalpostId;

    @JsonCreator
    public SøknadMetadata(@JsonProperty("egenskaper") SøknadEgenskap egenskaper,
            @JsonProperty("journalpostId") String journalpostId) {
        this.egenskaper = egenskaper;
        this.journalpostId = journalpostId;
    }

    @JsonIgnore
    public Versjon getVersjon() {
        return egenskaper.getVersjon();
    }

    @JsonIgnore
    public SøknadType getType() {
        return egenskaper.getType();
    }
}
