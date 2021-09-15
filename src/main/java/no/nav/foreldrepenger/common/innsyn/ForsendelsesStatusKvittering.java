package no.nav.foreldrepenger.common.innsyn;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ForsendelsesStatusKvittering(@JsonProperty("forsendelseStatus") ForsendelseStatus forsendelseStatus) {
    public static final ForsendelsesStatusKvittering PÅGÅR = new ForsendelsesStatusKvittering(ForsendelseStatus.PÅGÅR);

}
