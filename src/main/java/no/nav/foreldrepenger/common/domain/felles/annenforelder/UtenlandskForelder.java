package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import com.neovisionaries.i18n.CountryCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;


public record UtenlandskForelder(@NotBlank @Pattern(regexp = FRITEKST) String id,
                                 @NotNull CountryCode land,
                                 @Pattern(regexp = FRITEKST) String navn) implements AnnenForelder {

    @Override
    public boolean hasId() {
        return id != null;
    }

    @Override
    public String toString() {
        return "UtenlandskForelder{" +
                "id='" + mask(id) + '\'' +
                ", land=" + land +
                ", navn='" + mask(navn) + '\'' +
                '}';
    }
}
