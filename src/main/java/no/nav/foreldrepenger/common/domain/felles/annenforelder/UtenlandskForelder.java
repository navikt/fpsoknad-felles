package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import com.neovisionaries.i18n.CountryCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;


public final class UtenlandskForelder implements AnnenForelder {

    @NotBlank
    @Pattern(regexp = FRITEKST)
    private final String id;
    @NotNull
    private final CountryCode land;
    @Pattern(regexp = FRITEKST)
    private final String navn;

    public UtenlandskForelder(String id, CountryCode land, String navn) {
        this.id = id;
        this.land = land;
        this.navn = navn;
    }

    public String getId() {
        return id;
    }

    public CountryCode getLand() {
        return land;
    }

    public String getNavn() {
        return navn;
    }

    @Override
    public boolean hasId() {
        return id != null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UtenlandskForelder that = (UtenlandskForelder) o;
        return Objects.equals(id, that.id) &&
                land == that.land &&
                Objects.equals(navn, that.navn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, land, navn);
    }

    @Override
    public String toString() {
        return "UtenlandskForelder{" + "id='" + mask(id) + '\'' + ", land=" + land + ", navn='" + mask(navn) + '\'' + "} " + super.toString();
    }
}
