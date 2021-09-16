package no.nav.foreldrepenger.common.innsyn.vedtak.uttak;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Uttak {

    private final LocalDate førsteLovligeUttaksDato;
    private final List<UttaksPeriode> uttaksPerioder;

}
