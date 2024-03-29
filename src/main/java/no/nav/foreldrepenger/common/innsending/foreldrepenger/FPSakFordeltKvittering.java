package no.nav.foreldrepenger.common.innsending.foreldrepenger;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import no.nav.foreldrepenger.common.domain.Saksnummer;

public class FPSakFordeltKvittering extends FordelKvittering {

    static final String STATUS = "FPSAK";

    private final Saksnummer saksnummer;

    private final String journalpostId;

    @JsonCreator
    public FPSakFordeltKvittering(@JsonProperty("journalpostId") String journalpostId,
            @JsonProperty("saksnummer") Saksnummer saksnummer) {
        super(STATUS);
        this.journalpostId = journalpostId;
        this.saksnummer = saksnummer;
    }

    public String getJournalpostId() {
        return journalpostId;
    }

    public Saksnummer getSaksnummer() {
        return saksnummer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalpostId, saksnummer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FPSakFordeltKvittering other = (FPSakFordeltKvittering) obj;
        if (journalpostId == null) {
            if (other.journalpostId != null) {
                return false;
            }
        } else if (!journalpostId.equals(other.journalpostId)) {
            return false;
        }
        if (saksnummer == null) {
            if (other.saksnummer != null) {
                return false;
            }
        } else if (!saksnummer.equals(other.saksnummer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [journalpostId=" + journalpostId + ", saksnummer=" + saksnummer + "]";
    }
}
