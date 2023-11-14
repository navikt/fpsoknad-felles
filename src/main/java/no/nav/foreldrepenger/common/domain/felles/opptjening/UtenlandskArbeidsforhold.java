package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import org.hibernate.validator.constraints.Length;

import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;


public record UtenlandskArbeidsforhold(@Length(max = 100) @Pattern(regexp = FRITEKST) String arbeidsgiverNavn,
                                       @Valid @NotNull ÅpenPeriode periode,
                                       CountryCode land) {

    @Override
    public String toString() {
        return "UtenlandskArbeidsforhold{" +
                "arbeidsgiverNavn='" + mask(arbeidsgiverNavn) + '\'' +
                ", periode=" + periode +
                ", land=" + land +
                '}';
    }


}
