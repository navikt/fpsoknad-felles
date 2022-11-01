package no.nav.foreldrepenger.common.innsyn.v2;

import java.time.LocalDate;
import java.util.List;

public record AnnenPartVedtak(List<UttakPeriode> perioder, LocalDate termindato, Dekningsgrad dekningsgrad) {
}
