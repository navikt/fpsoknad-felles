package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;
import java.util.Objects;

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
                           boolean flerbarnsdager,
                           BrukerRolle forelder) {

    public boolean likBortsattFraTidsperiode(UttakPeriode that) { //Brukes for å slå sammen like perioder
        return flerbarnsdager == that.flerbarnsdager && kontoType == that.kontoType && Objects.equals(gradering, that.gradering)
                && oppholdÅrsak == that.oppholdÅrsak && morsAktivitet == that.morsAktivitet && Objects.equals(samtidigUttak,
                that.samtidigUttak) && Objects.equals(resultat, that.resultat) && utsettelseÅrsak == that.utsettelseÅrsak
                && overføringÅrsak == that.overføringÅrsak && forelder == that.forelder;
    }
}
