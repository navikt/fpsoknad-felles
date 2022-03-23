package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

public record SvpSak(Saksnummer saksnummer,
              Familiehendelse familiehendelse,
              Set<PersonDetaljer> barn,
              boolean sakAvsluttet,
              boolean gjelderAdopsjon) implements Sak {
}
