package no.nav.foreldrepenger.common.innsyn.v2;

import java.time.LocalDate;

public record Søknadsperiode(LocalDate fom, LocalDate tom, KontoType kontoType) {

}
