package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Adopsjon extends RelasjonTilBarn {

    @NotNull(message = "{ytelse.relasjontilbarn.adopsjon.omsorggsovertakelsesdato.notnull}")
    LocalDate omsorgsovertakelsesdato;
    boolean ektefellesBarn;
    boolean søkerAdopsjonAlene;

    LocalDate ankomstDato;
    List<@PastOrToday(message = "{ytelse.relasjontilbarn.adopsjon.fødselssdato.framtid}") LocalDate> fødselsdato;

    @Builder
    @JsonCreator
    public Adopsjon(@JsonProperty("antallBarn") int antallBarn,
            @JsonProperty("omsorgsovertakelsesdato") LocalDate omsorgsovertakelsesdato,
            @JsonProperty("ektefellesBarn") boolean ektefellesBarn,
            @JsonProperty("søkerAdopsjonAlene") boolean søkerAdopsjonAlene,
            @JsonProperty("vedlegg") List<String> vedlegg,
            @JsonProperty("ankomstDato") LocalDate ankomstDato,
            @JsonProperty("fødselsdato") List<LocalDate> fødselsdato) {
        super(antallBarn, vedlegg);
        this.omsorgsovertakelsesdato = omsorgsovertakelsesdato;
        this.ektefellesBarn = ektefellesBarn;
        this.ankomstDato = ankomstDato;
        this.fødselsdato = fødselsdato;
        this.søkerAdopsjonAlene = søkerAdopsjonAlene;
    }

    @Override
    public LocalDate relasjonsDato() {
        return fødselsdato.get(0);
    }
}
