package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.neovisionaries.i18n.CountryCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UtenlandskForelder extends AnnenForelder {

    @NotBlank
    @Pattern(regexp = FRITEKST)
    private final String id;
    @NotNull
    private final CountryCode land;
    @Pattern(regexp = FRITEKST)
    private final String navn;

    @Override
    public boolean hasId() {
        return id != null;
    }

    @Override
    public String toString() {
        return "UtenlandskForelder{" + "id='" + mask(id) + '\'' + ", land=" + land + ", navn='" + mask(navn) + '\'' + "} " + super.toString();
    }
}
