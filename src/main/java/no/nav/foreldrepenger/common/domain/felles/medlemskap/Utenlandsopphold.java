package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import static com.neovisionaries.i18n.CountryCode.NO;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.validation.annotations.UtenOverlapp;

public record Utenlandsopphold(@Valid @Size(max = 40) @UtenOverlapp List<@Valid @NotNull Opphold> opphold) {

    public Utenlandsopphold {
        opphold = Optional.ofNullable(opphold).orElse(List.of());
    }

    public boolean varINorge(LocalDate dato) {
        return NO.equals(landVedDato(dato));
    }

    public CountryCode landVedDato(LocalDate dato) {
        return Optional.ofNullable(dato)
                .map(d -> landVedDato(opphold, d))
                .orElse(NO);
    }

    private static CountryCode landVedDato(List<Opphold> utenlandsopphold, LocalDate dato) {
        return safeStream(utenlandsopphold)
                .filter(s -> s.varighet().isWithinPeriod(dato))
                .map(Opphold::land)
                .findFirst()
                .orElse(NO);
    }


    public record Opphold(@NotNull CountryCode land, @Valid @NotNull LukketPeriode varighet) {

        public LocalDate fom() {
            return varighet.fom();
        }

        public LocalDate tom() {
            return varighet.tom();
        }
    }
}
