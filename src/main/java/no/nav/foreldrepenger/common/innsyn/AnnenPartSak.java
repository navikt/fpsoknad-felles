package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;
import java.util.List;

public record AnnenPartSak(List<UttakPeriode> perioder, LocalDate termindato, Dekningsgrad dekningsgrad, int antallBarn) {
}
