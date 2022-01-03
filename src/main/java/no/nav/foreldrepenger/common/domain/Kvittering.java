package no.nav.foreldrepenger.common.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.nav.foreldrepenger.common.util.StringUtil.limit;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.FPSakFordeltKvittering;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.GosysKvittering;

@Data
@JsonInclude(NON_NULL)
public class Kvittering {

    private static final Logger LOG = LoggerFactory.getLogger(Kvittering.class);

    private String referanseId;
    private LocalDateTime mottattDato;
    private String journalId;
    private String saksNr;
    private byte[] pdf;
    private LocalDate førsteInntektsmeldingDag;
    private byte[] infoskrivPdf;

    @JsonCreator
    public Kvittering(@JsonProperty("mottattDato") LocalDateTime mottattDato,
            @JsonProperty("referanseId") String referanseId) {
        this.referanseId = referanseId;
        this.mottattDato = mottattDato;
    }

    public Kvittering() {
    }

    public static Kvittering ikkeSendt(byte[] pdf) {
        Kvittering kvittering = new Kvittering();
        kvittering.setPdf(pdf);
        return kvittering;
    }

    public static Kvittering fordeltKvittering(FPSakFordeltKvittering fordeltKvittering) {
        var kvittering = new Kvittering();
        kvittering.setJournalId(fordeltKvittering.getJournalpostId());
        kvittering.setSaksNr(fordeltKvittering.getSaksnummer());
        return kvittering;
    }

    public static Kvittering gosysKvittering(GosysKvittering gosysKvittering) {
        LOG.info("Søknaden er sendt til manuell behandling i Gosys, journalId er {}",
                gosysKvittering.getJournalpostId());
        Kvittering kvittering = new Kvittering();
        kvittering.setJournalId(gosysKvittering.getJournalpostId());
        return kvittering;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[referanseId=" + referanseId
                + ", mottattDato=" + mottattDato
                + ", journalId=" + journalId
                + ", saksNr=" + saksNr
                + ", pdf=" + limit(pdf, 20)
                + ", førsteInntektsmeldingDag=" + førsteInntektsmeldingDag
                + ", infoskrivPdf=" + limit(infoskrivPdf, 20) + "]";
    }

}
