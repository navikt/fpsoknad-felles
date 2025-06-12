package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public record UttakPeriode(@NotNull LocalDate fom,
                           @NotNull LocalDate tom,
                           KontoType kontoType,
                           UttakPeriodeResultat resultat,
                           UttakUtsettelseÅrsak utsettelseÅrsak,
                           UttakOppholdÅrsak oppholdÅrsak,
                           UttakOverføringÅrsak overføringÅrsak,
                           Gradering gradering,
                           MorsAktivitet morsAktivitet,
                           SamtidigUttak samtidigUttak,
                           boolean flerbarnsdager,
                           BrukerRolleSak forelder) {

    public boolean likBortsattFraTidsperiode(UttakPeriode that) { //Brukes for å slå sammen like perioder
        return flerbarnsdager == that.flerbarnsdager && kontoType == that.kontoType && Objects.equals(gradering, that.gradering)
                && oppholdÅrsak == that.oppholdÅrsak && morsAktivitet == that.morsAktivitet && Objects.equals(samtidigUttak,
                that.samtidigUttak) && Objects.equals(resultat, that.resultat) && utsettelseÅrsak == that.utsettelseÅrsak
                && overføringÅrsak == that.overføringÅrsak && forelder == that.forelder;
    }
}
