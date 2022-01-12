package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

@Data
@ToString(exclude = "vedlegg")
@EqualsAndHashCode(exclude = "vedlegg")
public class Frilans {

    private final ÅpenPeriode periode;
    private final boolean harInntektFraFosterhjem;
    private final boolean nyOppstartet;
    private final boolean jobberFremdelesSomFrilans;
    @Valid
    private final List<FrilansOppdrag> frilansOppdrag;
    private final List<String> vedlegg; // TODO: Har aldri blitt satt i foreldrepengeapi

    @JsonCreator
    public Frilans(@JsonProperty("periode") ÅpenPeriode periode,
                   @JsonProperty("harInntektFraFosterhjem") boolean harInntektFraFosterhjem,
                   @JsonProperty("nyOppstartet") boolean nyOppstartet,
                   @JsonProperty("jobberFremdelesSomFrilans") boolean jobberFremdelesSomFrilans,
                   @JsonProperty("frilansOppdrag") List<FrilansOppdrag> frilansOppdrag,
                   @JsonProperty("vedlegg") List<String> vedlegg) {
        this.periode = periode;
        this.harInntektFraFosterhjem = harInntektFraFosterhjem;
        this.nyOppstartet = nyOppstartet;
        this.jobberFremdelesSomFrilans = jobberFremdelesSomFrilans;
        this.frilansOppdrag = Optional.ofNullable(frilansOppdrag).orElse(emptyList());
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}
