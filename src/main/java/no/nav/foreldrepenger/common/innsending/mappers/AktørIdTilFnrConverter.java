package no.nav.foreldrepenger.common.innsending.mappers;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;

public interface AktørIdTilFnrConverter {

    AktørId konverter(Fødselsnummer fnr);

}
