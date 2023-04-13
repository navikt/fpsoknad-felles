package no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public final class FriUtsettelsesPeriode extends UtsettelsesPeriode {

    @JsonCreator
    public FriUtsettelsesPeriode(LocalDate fom, LocalDate tom, boolean erArbeidstaker,
            @NotNull UtsettelsesÅrsak årsak, MorsAktivitet morsAktivitetsType, List<VedleggReferanse> vedlegg) {
        super(fom, tom, erArbeidstaker, årsak, morsAktivitetsType, vedlegg);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ValgfrittVedlegg;
    }

    @Override
    public int hashCode() {
        return ValgfrittVedlegg.class.hashCode();
    }

    @Override
    public String toString() {
        return "FriUtsettelsesPeriode{}" + super.toString();
    }

}
