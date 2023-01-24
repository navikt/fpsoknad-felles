package no.nav.foreldrepenger.common.innsyn.v2;

public record SvpSak(Saksnummer saksnummer,
                     Familiehendelse familiehendelse,
                     boolean sakAvsluttet,
                     SvpÅpenBehandling åpenBehandling) implements Sak {
}
