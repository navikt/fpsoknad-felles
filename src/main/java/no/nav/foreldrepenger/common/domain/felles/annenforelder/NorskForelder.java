package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;

@Getter
@EqualsAndHashCode(exclude = { "navn" })
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

    @Override
    public String toString() {
        return "NorskForelder{" + "fnr=" + fnr + ", navn='" + mask(navn) + '\'' + "} " + super.toString();
    }
}
