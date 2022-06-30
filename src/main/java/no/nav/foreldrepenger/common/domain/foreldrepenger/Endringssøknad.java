package no.nav.foreldrepenger.common.domain.foreldrepenger;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.Saksnummer;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Endringssøknad extends Søknad {

    @Valid
    private final Saksnummer saksnr;

    public Endringssøknad(Saksnummer saksnr, LocalDate mottattdato, Søker søker, Ytelse ytelse, List<Vedlegg> vedlegg) {
        super(mottattdato, søker, ytelse, vedlegg);
        this.saksnr = saksnr;
    }

    public Endringssøknad(LocalDate mottattDato, Søker søker, Fordeling fordeling, Saksnummer saksnr,
            Vedlegg... vedlegg) {
        this(mottattDato, søker, fordeling, null, null, null, saksnr, vedlegg);
    }

    public Endringssøknad(LocalDate mottattDato, Søker søker, Fordeling fordeling, AnnenForelder annenForelder,
            Fødsel fødsel, Rettigheter rettigheter, Saksnummer saksnr,
            Vedlegg... vedlegg) {
        this(mottattDato, søker, fordeling, annenForelder, fødsel, rettigheter, saksnr, List.of(vedlegg));
    }

    @JsonCreator
    @Builder(builderMethodName = "EndringssøkandsBuilder")
    public Endringssøknad(@JsonProperty("mottattdato") LocalDate mottattDato,
            @JsonProperty("søker") Søker søker,
            @JsonProperty("fordeling") Fordeling fordeling,
            @JsonProperty("annenForelder") AnnenForelder annenForelder,
            @JsonProperty("fødsel") Fødsel fødsel,
            @JsonProperty("rettigheter") Rettigheter rettigheter,
            @JsonProperty("saksnr") Saksnummer saksnr,
            @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        super(mottattDato, søker, new Foreldrepenger(annenForelder, fødsel, rettigheter, null, null, fordeling, null),
                vedlegg);
        this.saksnr = saksnr;
    }

}
