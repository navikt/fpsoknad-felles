package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.innsyn.Arbeidstidprosent;

public record Tilrettelegging(@NotNull LocalDate fom,
                              @NotNull LocalDate tom,
                              TilretteleggingType type,
                              Arbeidstidprosent arbeidstidprosent,
                              PeriodeResultat resultat) {

}
