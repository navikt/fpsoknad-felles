package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Optional;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

public record NorskForelder(@Valid @NotNull Fødselsnummer fnr, @Pattern(regexp = FRITEKST) String navn) implements AnnenForelder {

    @Override
    public boolean hasId() {
        return Optional.ofNullable(fnr)
                .map(Fødselsnummer::value)
                .isPresent();
    }

    @Override
    public String toString() {
        return "NorskForelder{" +
                "fnr=" + fnr +
                ", navn='" + mask(navn) + '\'' +
                '}';
    }
}
