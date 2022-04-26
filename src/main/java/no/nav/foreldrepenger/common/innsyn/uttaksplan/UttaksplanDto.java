package no.nav.foreldrepenger.common.innsyn.uttaksplan;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

public record UttaksplanDto(SøknadsGrunnlagDto grunnlag, List<UttaksPeriodeDto> perioder) {

    public UttaksplanDto {
        perioder = Optional.ofNullable(perioder).orElse(emptyList());
    }
}
