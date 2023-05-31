package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public record FpSak(Saksnummer saksnummer,
                    boolean sakAvsluttet,
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
                    Dekningsgrad dekningsgrad,
                    LocalDateTime oppdatertTidspunkt) implements Sak {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FpSak fpSak = (FpSak) o;
        return sakAvsluttet == fpSak.sakAvsluttet && kanSøkeOmEndring == fpSak.kanSøkeOmEndring
                && sakTilhørerMor == fpSak.sakTilhørerMor && gjelderAdopsjon == fpSak.gjelderAdopsjon
                && morUføretrygd == fpSak.morUføretrygd
                && harAnnenForelderTilsvarendeRettEØS == fpSak.harAnnenForelderTilsvarendeRettEØS
                && ønskerJustertUttakVedFødsel == fpSak.ønskerJustertUttakVedFødsel
                && Objects.equals(saksnummer, fpSak.saksnummer)
                && rettighetType == fpSak.rettighetType
                && Objects.equals(annenPart, fpSak.annenPart) && Objects.equals(familiehendelse, fpSak.familiehendelse)
                && Objects.equals(gjeldendeVedtak, fpSak.gjeldendeVedtak) && Objects.equals(åpenBehandling, fpSak.åpenBehandling)
                && Objects.equals(barn, fpSak.barn) && dekningsgrad == fpSak.dekningsgrad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saksnummer, sakAvsluttet, kanSøkeOmEndring, sakTilhørerMor, gjelderAdopsjon,
                morUføretrygd, harAnnenForelderTilsvarendeRettEØS, ønskerJustertUttakVedFødsel, rettighetType, annenPart,
                familiehendelse, gjeldendeVedtak, åpenBehandling, barn, dekningsgrad);
    }
}
