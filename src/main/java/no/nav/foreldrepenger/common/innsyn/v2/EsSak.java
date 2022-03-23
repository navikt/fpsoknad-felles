package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

public record EsSak(Saksnummer saksnummer,
             Familiehendelse familiehendelse,
             EsVedtak gjeldendeVedtak,
             EsÅpenBehandling åpenBehandling,
             Set<PersonDetaljer> barn,
             boolean sakAvsluttet,
             boolean gjelderAdopsjon) implements Sak {
}
