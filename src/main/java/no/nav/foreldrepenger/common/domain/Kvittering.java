package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record Kvittering(LocalDateTime mottattDato, @Valid Saksnummer saksNr, byte[] pdf, byte[] infoskrivPdf) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kvittering that = (Kvittering) o;
        return Objects.equals(mottattDato, that.mottattDato) && Objects.equals(saksNr, that.saksNr) && Arrays.equals(pdf, that.pdf) && Arrays.equals(infoskrivPdf, that.infoskrivPdf);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mottattDato, saksNr);
        result = 31 * result + Arrays.hashCode(pdf);
        result = 31 * result + Arrays.hashCode(infoskrivPdf);
        return result;
    }

    @Override
    public String toString() {
        return "Kvittering{" +
                "mottattDato=" + mottattDato +
                ", saksNr=" + saksNr +
                '}';
    }
}
