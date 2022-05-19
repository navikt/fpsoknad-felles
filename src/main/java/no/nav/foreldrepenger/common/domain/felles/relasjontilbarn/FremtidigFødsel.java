package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class FremtidigFødsel extends RelasjonTilBarn {
    private final LocalDate terminDato;
    @PastOrToday(nullable = true)
    private final LocalDate utstedtDato;

    public FremtidigFødsel(LocalDate terminDato, LocalDate utstedtDato) {
        this(1, terminDato, utstedtDato, emptyList());
    }

    @Builder
    @JsonCreator
    public FremtidigFødsel(int antallBarn, LocalDate terminDato, LocalDate utstedtDato, List<String> vedlegg) {
        super(antallBarn, vedlegg);
        this.terminDato = terminDato;
        this.utstedtDato = utstedtDato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return terminDato;
    }
}
