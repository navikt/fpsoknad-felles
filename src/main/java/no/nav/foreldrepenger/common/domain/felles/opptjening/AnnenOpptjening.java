package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

@Data
@ToString(exclude = "vedlegg")
@EqualsAndHashCode(exclude = "vedlegg")
public class AnnenOpptjening {

    private final AnnenOpptjeningType type;
    @Valid
    private final ÅpenPeriode periode;
    private final List<@Pattern(regexp = FRITEKST) String> vedlegg;

    @JsonCreator
    public AnnenOpptjening(@JsonProperty("type") AnnenOpptjeningType type, @JsonProperty("periode") ÅpenPeriode periode,
            @JsonProperty("vedlegg") List<String> vedlegg) {
        this.type = type;
        this.periode = periode;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}
