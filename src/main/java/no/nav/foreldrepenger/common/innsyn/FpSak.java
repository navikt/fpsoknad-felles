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
                    Person annenPart,
                    Familiehendelse familiehendelse,
                    FpVedtak gjeldendeVedtak,
                    FpÅpenBehandling åpenBehandling,
                    Set<Person> barn,
                    Dekningsgrad dekningsgrad) implements Sak {
}
