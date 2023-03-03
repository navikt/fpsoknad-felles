package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;

public record UttakPeriode(LocalDate fom,
                           LocalDate tom,
                           KontoType kontoType,
                           UttakPeriodeResultat resultat,
                           UtsettelseÅrsak utsettelseÅrsak,
                           OppholdÅrsak oppholdÅrsak,
                           OverføringÅrsak overføringÅrsak,
                           Gradering gradering,
                           MorsAktivitet morsAktivitet,
                           SamtidigUttak samtidigUttak,
                           boolean flerbarnsdager) {
}
