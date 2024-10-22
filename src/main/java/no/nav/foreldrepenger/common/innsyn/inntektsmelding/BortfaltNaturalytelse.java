package no.nav.foreldrepenger.common.innsyn.inntektsmelding;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BortfaltNaturalytelse(LocalDate fomDato, LocalDate tomDato, BigDecimal beløpPerMnd, NaturalytelseType type) {
}
