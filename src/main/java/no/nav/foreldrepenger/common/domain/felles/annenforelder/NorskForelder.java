package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "navn" })
public final class NorskForelder extends AnnenForelder {

    @NotNull
    @Valid
    private final Fødselsnummer fnr;
    @Pattern(regexp = FRITEKST)
    private final String navn;

    public NorskForelder(Fødselsnummer fnr) {
        this(fnr, null);
    }

    @JsonCreator
    public NorskForelder(@JsonProperty("fnr") Fødselsnummer fnr, @JsonProperty("navn") String navn) {
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
