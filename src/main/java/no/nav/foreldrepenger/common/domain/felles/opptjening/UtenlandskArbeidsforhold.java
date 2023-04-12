package no.nav.foreldrepenger.common.domain.felles.opptjening;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;


public record UtenlandskArbeidsforhold(@Length(max = 100) @Pattern(regexp = FRITEKST) String arbeidsgiverNavn,
                                       @Valid @NotNull ÅpenPeriode periode,
                                       @Valid List<VedleggReferanse> vedlegg,
                                       CountryCode land) {

    @JsonCreator
    public UtenlandskArbeidsforhold {
        vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtenlandskArbeidsforhold that = (UtenlandskArbeidsforhold) o;
        return Objects.equals(arbeidsgiverNavn, that.arbeidsgiverNavn) && Objects.equals(periode, that.periode) && Objects.equals(vedlegg, that.vedlegg) && land == that.land;
    }

    @Override
    public int hashCode() {
        return Objects.hash(arbeidsgiverNavn, periode, vedlegg, land);
    }

    @Override
    public String toString() {
        return "UtenlandskArbeidsforhold{" +
                "arbeidsgiverNavn='" + mask(arbeidsgiverNavn) + '\'' +
                ", periode=" + periode +
                ", vedlegg=" + vedlegg +
                ", land=" + land +
                '}';
    }


}
