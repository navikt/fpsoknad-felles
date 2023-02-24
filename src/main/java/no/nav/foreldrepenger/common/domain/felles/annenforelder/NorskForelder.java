package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Optional;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

public final class NorskForelder implements AnnenForelder {

    @Valid
    @NotNull
    private final Fødselsnummer fnr;
    @Pattern(regexp = FRITEKST)
    private final String navn;

    @JsonCreator
    public NorskForelder(Fødselsnummer fnr, String navn) {
        this.fnr = fnr;
        this.navn = navn;
    }

    @Override
    public boolean hasId() {
        return Optional.ofNullable(fnr)
                .map(Fødselsnummer::value)
                .isPresent();
    }

    public Fødselsnummer getFnr() {
        return fnr;
    }

    public String getNavn() {
        return navn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NorskForelder that = (NorskForelder) o;
        return Objects.equals(fnr, that.fnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fnr);
    }

    @Override
    public String toString() {
        return "NorskForelder{" + "fnr=" + fnr + ", navn='" + mask(navn) + '\'' + "} " + super.toString();
    }
}
