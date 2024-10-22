package no.nav.foreldrepenger.common.innsyn.inntektsmelding;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Refusjon(BigDecimal refusjonsbeløpMnd, LocalDate fomDato) {
}
