package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.innsending.SøknadType.ENDRING_FORELDREPENGER;
import static no.nav.foreldrepenger.common.innsending.SøknadType.INITIELL_ENGANGSSTØNAD;
import static no.nav.foreldrepenger.common.innsending.SøknadType.INITIELL_FORELDREPENGER;
import static no.nav.foreldrepenger.common.innsending.SøknadType.INITIELL_SVANGERSKAPSPENGER;
import static no.nav.foreldrepenger.common.util.Versjon.DEFAULT_SVP_VERSJON;
import static no.nav.foreldrepenger.common.util.Versjon.DEFAULT_VERSJON;

import java.util.Arrays;
import java.util.List;

import no.nav.foreldrepenger.common.innsending.SøknadType;
import no.nav.foreldrepenger.common.innsyn.SøknadEgenskap;
import no.nav.foreldrepenger.common.util.Versjon;

public class MapperEgenskaper {
    private final List<SøknadEgenskap> egenskaper;

    public static final MapperEgenskaper ENGANGSSTØNAD = new MapperEgenskaper(INITIELL_ENGANGSSTØNAD);
    public static final MapperEgenskaper SVANGERSKAPSPENGER = new MapperEgenskaper(DEFAULT_SVP_VERSJON, INITIELL_SVANGERSKAPSPENGER);
    public static final MapperEgenskaper FORELDREPENGER = new MapperEgenskaper(INITIELL_FORELDREPENGER, ENDRING_FORELDREPENGER);
    public static final MapperEgenskaper UKJENT = new MapperEgenskaper(Versjon.UKJENT, SøknadType.UKJENT);


    public MapperEgenskaper(Versjon versjon, SøknadType type) {
        this(new SøknadEgenskap(versjon, type));
    }

    public static MapperEgenskaper of(SøknadType... typer) {
        return new MapperEgenskaper(typer);
    }

    private MapperEgenskaper(SøknadType... typer) {
        this(DEFAULT_VERSJON, typer);
    }

    public MapperEgenskaper(Versjon versjon, SøknadType... typer) {
        this(typerForVersjon(versjon, typer));
    }

    MapperEgenskaper(SøknadEgenskap... egenskaper) {
        this(List.of(egenskaper));
    }

    MapperEgenskaper(List<SøknadEgenskap> egenskaper) {
        this.egenskaper = egenskaper;
    }

    public List<SøknadEgenskap> getEgenskaper() {
        return egenskaper;
    }

    public boolean kanMappe(SøknadEgenskap egenskap) {
        return egenskaper.contains(egenskap);
    }

    private static List<SøknadEgenskap> typerForVersjon(final Versjon versjon, SøknadType... typer) {
        return Arrays.stream(typer)
                .map(type -> new SøknadEgenskap(versjon, type))
                .toList();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [mapperEgenskaper=" + egenskaper + "]";
    }
}
