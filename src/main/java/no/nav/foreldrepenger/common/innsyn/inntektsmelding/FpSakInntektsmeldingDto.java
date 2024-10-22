package no.nav.foreldrepenger.common.innsyn.inntektsmelding;

import com.fasterxml.jackson.annotation.JsonInclude;

import no.nav.foreldrepenger.common.domain.ArbeidsgiverIdentifikator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FpSakInntektsmeldingDto(
        Boolean erAktiv,
        BigDecimal stillingsprosent,
        BigDecimal inntektPrMnd,
        BigDecimal refusjonPrMnd,
        String arbeidsgiverNavn,
        String arbeidsgiverIdent,
        String journalpostId,
        LocalDateTime mottattTidspunkt,
        LocalDate startDatoPermisjon,
        List<BortfaltNaturalytelse> bortfalteNaturalytelser,
        List<Refusjon> refusjonsperioder
) { }
