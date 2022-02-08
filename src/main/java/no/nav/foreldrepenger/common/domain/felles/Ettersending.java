package no.nav.foreldrepenger.common.domain.felles;

import static java.util.Arrays.asList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public final class Ettersending {

    @NotNull
    private final String saksnr;
    @NotNull
    private final EttersendingsType type;
    @Valid
    private final List<Vedlegg> vedlegg;
    @Pattern(regexp = FRITEKST)
    private String dialogId;

    public Ettersending(EttersendingsType type, String saksnr, Vedlegg... vedlegg) {
        this(type, saksnr, asList(vedlegg));
    }

    @JsonCreator
    public Ettersending(@JsonProperty("type") EttersendingsType type,
            @JsonProperty("saksnr") String saksnr,
            @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        this.type = type;
        this.saksnr = saksnr;
        this.vedlegg = vedlegg;
    }
}
