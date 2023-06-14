package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

import no.nav.foreldrepenger.common.innsyn.Arbeidstidprosent;

public record TilretteleggingPeriode(LocalDate fom,
                                     LocalDate tom,
                                     TilretteleggingType type,
                                     Arbeidstidprosent arbeidstidprosent) {

}
