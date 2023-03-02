package no.nav.foreldrepenger.common.innsending;

import static no.nav.foreldrepenger.common.util.Versjon.defaultVersjon;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import no.nav.foreldrepenger.common.util.Versjon;

public class SøknadEgenskap implements Serializable {
    public static final SøknadEgenskap INITIELL_SVANGERSKAPSPENGER = of(SøknadType.INITIELL_SVANGERSKAPSPENGER);
    public static final SøknadEgenskap INITIELL_ENGANGSSTØNAD = of(SøknadType.INITIELL_ENGANGSSTØNAD);
    public static final SøknadEgenskap INITIELL_FORELDREPENGER = of(SøknadType.INITIELL_FORELDREPENGER);
    public static final SøknadEgenskap ENDRING_FORELDREPENGER = of(SøknadType.ENDRING_FORELDREPENGER);
    public static final SøknadEgenskap ETTERSENDING_SVANGERSKAPSPENGER = of(SøknadType.ETTERSENDING_SVANGERSKAPSPENGER);
    public static final SøknadEgenskap ETTERSENDING_FORELDREPENGER = of(SøknadType.ETTERSENDING_FORELDREPENGER);
    public static final SøknadEgenskap ETTERSENDING_ENGANGSSTØNAD = of(SøknadType.ETTERSENDING_ENGANGSSTØNAD);
    public static final SøknadEgenskap UKJENT = new SøknadEgenskap(Versjon.UKJENT, SøknadType.UKJENT);

    private final Versjon versjon;
    private final SøknadType type;

    public static SøknadEgenskap of(SøknadType type) {
        return new SøknadEgenskap(defaultVersjon(type), type);
    }

    @JsonCreator
    public SøknadEgenskap(@JsonProperty("versjon") Versjon versjon, @JsonProperty("type") SøknadType type) {
        this.versjon = versjon;
        this.type = type;
    }

    public Versjon getVersjon() {
        return versjon;
    }

    public SøknadType getType() {
        return type;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SøknadEgenskap that = (SøknadEgenskap) o;
        return versjon == that.versjon && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(versjon, type);
    }

    @Override
    public String toString() {
        return "SøknadEgenskap{" + "versjon=" + versjon + ", type=" + type + '}';
    }
}
