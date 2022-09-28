package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Adopsjon extends RelasjonTilBarn {

    @NotNull(message = "{ytelse.relasjontilbarn.adopsjon.omsorggsovertakelsesdato.notnull}")
    private final LocalDate omsorgsovertakelsesdato;
    private final boolean ektefellesBarn;
    private final boolean søkerAdopsjonAlene;
    private final LocalDate ankomstDato;
    private final List<@PastOrToday(message = "{ytelse.relasjontilbarn.adopsjon.fødselssdato.framtid}") LocalDate> fødselsdato;

    @Builder
    @JsonCreator
    public Adopsjon(int antallBarn, LocalDate omsorgsovertakelsesdato, boolean ektefellesBarn, boolean søkerAdopsjonAlene,
                    List<String> vedlegg, LocalDate ankomstDato, List<LocalDate> fødselsdato) {
        super(antallBarn, vedlegg);
        this.omsorgsovertakelsesdato = omsorgsovertakelsesdato;
        this.ektefellesBarn = ektefellesBarn;
        this.ankomstDato = ankomstDato;
        this.fødselsdato = fødselsdato;
        this.søkerAdopsjonAlene = søkerAdopsjonAlene;
    }

    @Override
    public LocalDate relasjonsDato() {
        return omsorgsovertakelsesdato;
    }
}
