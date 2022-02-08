package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.neovisionaries.i18n.CountryCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
}
