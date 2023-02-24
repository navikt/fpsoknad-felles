package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;

public record Fordeling(Boolean erAnnenForelderInformert, @Valid List<LukketPeriodeMedVedlegg> perioder, Boolean ønskerJustertUttakVedFødsel) {

    @JsonCreator
    public Fordeling {
        perioder = Optional.ofNullable(perioder).orElse(emptyList());
    }

    @JsonIgnore
    public LocalDate getFørsteUttaksdag() {
        return perioder.stream()
                .sorted()
                .filter(not(erFriPeriode()))
                .filter(aktuellPeriode())
                .findFirst()
                .map(LukketPeriodeMedVedlegg::getFom)
                .orElse(null);
    }

    private static Predicate<? super LukketPeriodeMedVedlegg> aktuellPeriode() {
        return f -> f instanceof UttaksPeriode || f instanceof UtsettelsesPeriode || f instanceof OverføringsPeriode;
    }

    private static Predicate<? super LukketPeriodeMedVedlegg> erFriPeriode() {
        return p -> p instanceof UtsettelsesPeriode up && up.getÅrsak().equals(UtsettelsesÅrsak.FRI);
    }
}
