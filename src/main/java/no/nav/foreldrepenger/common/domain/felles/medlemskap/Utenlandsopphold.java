package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import java.time.LocalDate;

import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;

public record Utenlandsopphold(@NotNull CountryCode land, @Valid @NotNull LukketPeriode varighet) {

    public LocalDate fom() {
        return varighet.fom();
    }

    public LocalDate tom() {
        return varighet.tom();
    }
}
