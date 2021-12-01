package no.nav.foreldrepenger.common.dto.svangerskapspenger.tilrettelegging;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.dto.felles.ProsentAndel;
import no.nav.foreldrepenger.common.dto.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelvisTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate tilrettelagtArbeidFom;
    @NotNull
    private final ProsentAndel stillingsprosent;

    public DelvisTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom,
                                 LocalDate tilrettelagtArbeidFom,
                                 ProsentAndel stillingsprosent, List<String> vedlegg) {
        super(arbeidsforhold, behovForTilretteleggingFom, vedlegg);
        this.tilrettelagtArbeidFom = tilrettelagtArbeidFom;
        this.stillingsprosent = stillingsprosent;
    }
}
