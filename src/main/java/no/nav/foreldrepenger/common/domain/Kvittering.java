package no.nav.foreldrepenger.common.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BARE_TALL;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Value;

@JsonInclude(NON_NULL)
@Value //Problemer med jackson og byte[] hvis man gjør om til record
public class Kvittering {
    LocalDateTime mottattDato;
    @Pattern(regexp = BARE_TALL)
    String saksNr;
    byte[] pdf;
    byte[] infoskrivPdf;

    @Override
    public String toString() {
        return "Kvittering{" + "mottattDato=" + mottattDato + ", saksNr='" + saksNr + '\'' + '}';
    }
}
