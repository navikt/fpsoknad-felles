package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

public record FpSak(Saksnummer saksnummer,
             boolean sakAvsluttet,
             boolean kanSøkeOmEndring,
             boolean sakTilhørerMor,
             boolean gjelderAdopsjon,
             boolean morUføretrygd,
             boolean annenPartHarRettPåForeldrepengerIEØS,
             RettighetType rettighetType,
             AnnenPart annenPart,
             Familiehendelse familiehendelse,
             FpVedtak gjeldendeVedtak,
             FpÅpenBehandling åpenBehandling,
             Set<PersonDetaljer> barn,
             Dekningsgrad dekningsgrad) implements Sak {
}
