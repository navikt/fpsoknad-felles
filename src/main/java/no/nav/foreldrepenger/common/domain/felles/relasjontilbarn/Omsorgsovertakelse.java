package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Omsorgsovertakelse extends RelasjonTilBarn {

    private final LocalDate omsorgsovertakelsesdato;
    private final List<LocalDate> fødselsdato;

    public Omsorgsovertakelse(LocalDate omsorgsovertakelsesdato, LocalDate fødselsdato) {
        this(1, omsorgsovertakelsesdato, singletonList(fødselsdato), emptyList());
    }

    @Builder
    @JsonCreator
    public Omsorgsovertakelse(int antallBarn, LocalDate omsorgsovertakelsesdato, List<LocalDate> fødselsdato, List<String> vedlegg) {
        super(antallBarn, vedlegg);
        this.omsorgsovertakelsesdato = omsorgsovertakelsesdato;
        this.fødselsdato = fødselsdato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return omsorgsovertakelsesdato;
    }
}
