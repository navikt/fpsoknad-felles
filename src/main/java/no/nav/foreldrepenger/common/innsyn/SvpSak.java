package no.nav.foreldrepenger.common.innsyn;

public record SvpSak(Saksnummer saksnummer,
                     Familiehendelse familiehendelse,
                     boolean sakAvsluttet,
                     SvpÅpenBehandling åpenBehandling) implements Sak {
}
