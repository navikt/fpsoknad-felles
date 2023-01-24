package no.nav.foreldrepenger.common.innsyn.v2;

public record EsSak(Saksnummer saksnummer,
                    Familiehendelse familiehendelse,
                    boolean sakAvsluttet,
                    EsÅpenBehandling åpenBehandling,
                    boolean gjelderAdopsjon) implements Sak {
}
