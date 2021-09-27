package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FremtidigFødsel extends RelasjonTilBarn {
    LocalDate terminDato;
    @PastOrToday(nullable = true)
    LocalDate utstedtDato;

    public FremtidigFødsel(LocalDate terminDato, LocalDate utstedtDato) {
        this(1, terminDato, utstedtDato, emptyList());
    }

    @JsonCreator
    public FremtidigFødsel(int antallBarn, LocalDate terminDato,
            LocalDate utstedtDato, List<String> vedlegg) {
        super(antallBarn, vedlegg);
        this.terminDato = terminDato;
        this.utstedtDato = utstedtDato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return terminDato;
    }
}
