package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;
import java.util.Set;

public record FpSak(Saksnummer saksnummer,
                    boolean sakAvsluttet,
                    LocalDate sisteSøknadMottattDato,
                    boolean kanSøkeOmEndring,
                    boolean sakTilhørerMor,
                    boolean gjelderAdopsjon,
                    boolean morUføretrygd,
                    boolean harAnnenForelderTilsvarendeRettEØS,
                    boolean ønskerJustertUttakVedFødsel,
                    RettighetType rettighetType,
                    AnnenPart annenPart,
                    Familiehendelse familiehendelse,
                    FpVedtak gjeldendeVedtak,
                    FpÅpenBehandling åpenBehandling,
                    Set<PersonDetaljer> barn,
                    Dekningsgrad dekningsgrad) implements Sak {
}
