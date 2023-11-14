package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;


public final class FriUtsettelsesPeriode extends UtsettelsesPeriode {

    @JsonCreator
    public FriUtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker,
            @NotNull UtsettelsesÅrsak årsak, MorsAktivitet morsAktivitetsType) {
        super(fom, tom, erArbeidstaker, årsak, morsAktivitetsType);
    }
    
    @Override
    public String toString() {
        return "FriUtsettelsesPeriode{}" + super.toString();
    }

}
