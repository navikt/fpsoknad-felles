package no.nav.foreldrepenger.common.domain.foreldrepenger;

import static java.util.Arrays.asList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Endringssøknad extends Søknad {

    @NotNull
    @Pattern(regexp = FRITEKST)
    String saksnr;

    public Endringssøknad(String saksnr, LocalDate mottattdato, Søker søker, Ytelse ytelse, List<Vedlegg> vedlegg) {
        super(mottattdato, søker, ytelse, vedlegg);
        this.saksnr = saksnr;
    }

    public Endringssøknad(LocalDate mottattDato, Søker søker, Fordeling fordeling, String saksnr,
            Vedlegg... vedlegg) {
        this(mottattDato, søker, fordeling, null, null, null, saksnr, vedlegg);
    }

    public Endringssøknad(LocalDate mottattDato, Søker søker, Fordeling fordeling, AnnenForelder annenForelder,
            Fødsel fødsel, Rettigheter rettigheter, String saksnr,
            Vedlegg... vedlegg) {
        this(mottattDato, søker, fordeling, annenForelder, fødsel, rettigheter, saksnr, asList(vedlegg));
    }

    @Builder(builderMethodName = "EndringssøkandsBuilder")
    @JsonCreator
    public Endringssøknad(@JsonProperty("mottattdato") LocalDate mottattDato,
            @JsonProperty("søker") Søker søker,
            @JsonProperty("fordeling") Fordeling fordeling,
            @JsonProperty("annenForelder") AnnenForelder annenForelder,
            @JsonProperty("fødsel") Fødsel fødsel,
            @JsonProperty("rettigheter") Rettigheter rettigheter,
            @JsonProperty("saksnr") String saksnr,
            @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        super(mottattDato, søker, new Foreldrepenger(annenForelder, fødsel, rettigheter, null, null, fordeling, null),
                vedlegg);
        this.saksnr = saksnr;
    }

}
