package no.nav.foreldrepenger.common.innsyn;

import static no.nav.foreldrepenger.common.util.Versjon.defaultVersjon;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import no.nav.foreldrepenger.common.innsending.SøknadType;
import no.nav.foreldrepenger.common.util.Pair;
import no.nav.foreldrepenger.common.util.Versjon;

public class SøknadEgenskap {
    public static final SøknadEgenskap INITIELL_SVANGERSKAPSPENGER = of(SøknadType.INITIELL_SVANGERSKAPSPENGER);
    public static final SøknadEgenskap INITIELL_ENGANGSSTØNAD = of(SøknadType.INITIELL_ENGANGSSTØNAD);
    public static final SøknadEgenskap INITIELL_FORELDREPENGER = of(SøknadType.INITIELL_FORELDREPENGER);
    public static final SøknadEgenskap ENDRING_FORELDREPENGER = of(SøknadType.ENDRING_FORELDREPENGER);
    public static final SøknadEgenskap ETTERSENDING_SVANGERSKAPSPENGER = of(SøknadType.ETTERSENDING_SVANGERSKAPSPENGER);
    public static final SøknadEgenskap ETTERSENDING_FORELDREPENGER = of(SøknadType.ETTERSENDING_FORELDREPENGER);
    public static final SøknadEgenskap ETTERSENDING_ENGANGSSTØNAD = of(SøknadType.ETTERSENDING_ENGANGSSTØNAD);
    public static final SøknadEgenskap UKJENT = new SøknadEgenskap(Versjon.UKJENT, SøknadType.UKJENT);

    private final Pair<Versjon, SøknadType> egenskap;

    public static SøknadEgenskap of(SøknadType type) {
        return new SøknadEgenskap(defaultVersjon(type), type);
    }

    @JsonCreator
    public SøknadEgenskap(@JsonProperty("versjon") Versjon versjon, @JsonProperty("type") SøknadType type) {
        this.egenskap = Pair.of(versjon, type);
    }

    public Pair<Versjon, SøknadType> getEgenskap() {
        return egenskap;
    }

    public Versjon getVersjon() {
        return egenskap.getFirst();
    }

    public SøknadType getType() {
        return egenskap.getSecond();
    }

    public boolean erEttersending() {
        return getType().erEttersending();
    }

    public boolean erEndring() {
        return getType().erEndring();
    }

    public boolean erInitiellForeldrepenger() {
        return getType().erInitiellForeldrepenger();
    }

    @Override
    public int hashCode() {
        return Objects.hash(egenskap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SøknadEgenskap other = (SøknadEgenskap) obj;
        return Objects.equals(getVersjon(), other.getVersjon()) && Objects.equals(getType(), other.getType());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [versjon=" + egenskap.getFirst() + ", søknadType=" + egenskap.getSecond()
                + "]";
    }

}
