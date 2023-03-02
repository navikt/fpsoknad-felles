package no.nav.foreldrepenger.common.innsyn;

public record EsSak(Saksnummer saksnummer,
                    Familiehendelse familiehendelse,
                    boolean sakAvsluttet,
                    EsÅpenBehandling åpenBehandling,
                    boolean gjelderAdopsjon) implements Sak {
}
