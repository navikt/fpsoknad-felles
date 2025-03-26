package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.innsyn.Aktivitet;

public record SvpArbeidsforhold(@NotNull @Valid Aktivitet aktivitet,
                                LocalDate behovFrom,
                                String risikofaktorer,
                                String tiltak,
                                @NotNull Set<@Valid @NotNull Tilrettelegging> tilrettelegginger,
                                @NotNull Set<@Valid @NotNull OppholdPeriode> oppholdsperioder,
                                AvslutningÅrsak avslutningÅrsak) {
}
