package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;

public record Utenlandsopphold(@NotNull CountryCode land, @Valid LukketPeriode varighet) {

    public LocalDate fom() {
        return varighet.fom();
    }

    public LocalDate tom() {
        return varighet.tom();
    }
}
