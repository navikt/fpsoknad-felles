package no.nav.foreldrepenger.common.domain.felles;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import no.nav.foreldrepenger.common.domain.Saksnummer;

public record Ettersending(@Valid Saksnummer saksnr,
                           @NotNull EttersendingsType type,
                           @Valid List<Vedlegg> vedlegg,
                           @Pattern(regexp = FRITEKST) String dialogId) {

    public Ettersending {
        vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}
