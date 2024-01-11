package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import static com.neovisionaries.i18n.CountryCode.NO;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.validation.annotations.Opphold;

@JsonPropertyOrder({ "tidligereUtenlandsopphold", "framtidigUtenlandsopphold" })
public record Medlemsskap(@Valid @Opphold(fortid = true) List<Utenlandsopphold> tidligereUtenlandsopphold,
                          @Valid @Opphold List<Utenlandsopphold> framtidigUtenlandsopphold) {

    public Medlemsskap(@Valid List<Utenlandsopphold> tidligereUtenlandsopphold,
                       @Valid List<Utenlandsopphold> framtidigUtenlandsopphold) {
        this.tidligereUtenlandsopphold = Optional.ofNullable(tidligereUtenlandsopphold).orElse(emptyList());
        this.framtidigUtenlandsopphold = Optional.ofNullable(framtidigUtenlandsopphold).orElse(emptyList());
    }

    @JsonIgnore
    public boolean isNorgeNeste12() {
        return framtidigUtenlandsopphold.isEmpty();
    }

    @JsonIgnore
    public boolean isBoddINorge() {
        return tidligereUtenlandsopphold.isEmpty();
    }

    public boolean varUtenlands(LocalDate dato) {
        return !varINorge(dato);
    }

    public boolean varINorge(LocalDate dato) {
        return NO.equals(landVedDato(dato));
    }

    public CountryCode landVedDato(LocalDate dato) {
        return Optional.ofNullable(dato)
                .map(d -> landVedDato(utenlandsOpphold(), d))
                .orElse(NO);
    }

    private static CountryCode landVedDato(List<Utenlandsopphold> utenlandsopphold, LocalDate dato) {
        return safeStream(utenlandsopphold)
                .filter(s -> s.varighet().isWithinPeriod(dato))
                .map(Utenlandsopphold::land)
                .findFirst()
                .orElse(NO);
    }

    public List<Utenlandsopphold> utenlandsOpphold() {
        return Stream
                .concat(safeStream(tidligereUtenlandsopphold),
                        safeStream(framtidigUtenlandsopphold))
                .toList();
    }
}
