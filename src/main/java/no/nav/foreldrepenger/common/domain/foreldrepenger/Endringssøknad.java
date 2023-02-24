package no.nav.foreldrepenger.common.domain.foreldrepenger;

import no.nav.foreldrepenger.common.domain.Saksnummer;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public final class Endringssøknad extends Søknad {

    @Valid
    private final Saksnummer saksnr;

    public Endringssøknad(LocalDate mottattdato, Søker søker, Ytelse ytelse, String tilleggsopplysninger, List<Vedlegg> vedlegg, Saksnummer saksnr) {
        super(mottattdato, søker, ytelse, tilleggsopplysninger, vedlegg);
        this.saksnr = saksnr;
    }

    public Saksnummer getSaksnr() {
        return saksnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Endringssøknad that = (Endringssøknad) o;
        return Objects.equals(saksnr, that.saksnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), saksnr);
    }

    @Override
    public String toString() {
        return "Endringssøknad{" +
                "saksnr=" + saksnr +
                "} " + super.toString();
    }
}
