package no.nav.foreldrepenger.common.dto.foreldrepenger;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import no.nav.foreldrepenger.common.dto.Søker;
import no.nav.foreldrepenger.common.dto.Søknad;
import no.nav.foreldrepenger.common.dto.felles.Vedlegg;
import no.nav.foreldrepenger.common.dto.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.dto.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.dto.foreldrepenger.fordeling.Fordeling;


public class Endringssøknad extends Søknad {

    String saksnr;

    @JsonCreator
    public Endringssøknad(@JsonProperty("mottattdato") LocalDate mottattDato,
            @JsonProperty("søker") Søker søker,
            @JsonProperty("fordeling") Fordeling fordeling,
            @JsonProperty("annenForelder") AnnenForelder annenForelder,
            @JsonProperty("fødsel") Fødsel fødsel,
            @JsonProperty("rettigheter") Rettigheter rettigheter,
            @JsonProperty("saksnr") String saksnr,
            @JsonProperty("tilleggsopplysninger") String tilleggsopplysninger,
            @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        super(mottattDato, søker, new Foreldrepenger(annenForelder, fødsel, rettigheter, null, null, fordeling, null),
                tilleggsopplysninger, vedlegg);
        this.saksnr = saksnr;
    }

}
