package no.nav.foreldrepenger.common.domain.felles;

public enum DokumentType {
    I000001("Søknad om svangerskapspenger"),
    I000002("Søknad om foreldrepenger ved adopsjon"),
    I000003("Søknad om engangsstønad ved fødsel"),
    I000004("Søknad om engangsstønad ved adopsjon"),
    I000005("Søknad om foreldrepenger ved fødsel"),
    I000006("Utsettelse eller gradert uttak av foreldrepenger (fleksibelt uttak)"),
    I000027("Klage/anke"),
    I000050("Søknad om endring av uttak av foreldrepenger eller overføring av kvote"),

    I000007("Inntektsopplysninger om selvstendig næringsdrivende og/eller frilansere som skal ha foreldrepenger eller svangerskapspenger"),
    I000023("Legeerklæring"),
    I000026("Inntektsopplysninger for arbeidstaker som skal ha sykepenger, foreldrepenger, svangerskapspenger, pleie-/opplæringspenger"),
    I000032("Resultatregnskap"),
    I000036("Dokumentasjon av ferie"),
    I000037("Dokumentasjon av innleggelse i helseinstitusjon"),
    I000038("Dokumentasjon av mors utdanning, arbeid eller sykdom"),
    I000039("Dokumentasjon av militær- eller siviltjeneste"),
    I000041("Dokumentasjon av termindato (lev. kun av mor), fødsel eller dato for omsorgsovertakelse"),
    I000042("Dokumentasjon av dato for overtakelse av omsorg"),
    I000043("Dokumentasjon av arbeidsforhold"),
    I000044("Dokumentasjon av etterlønn/sluttvederlag"),
    I000045("Beskrivelse av funksjonsnedsettelse"),
    I000047("Brukeropplastet dokumentasjon"),
    I000049("Annet skjema (ikke NAV-skjema)"),
    I000051("Bekreftelse på deltakelse i kvalifiseringsprogrammet"),
    I000060("Annet"),
    I000061("Bekreftelse fra studiested/skole"),
    I000062("Bekreftelse på ventet fødselsdato"),
    I000063("Fødselsattest"),
    I000064("Elevdokumentasjon fra lærested"),
    I000065("Bekreftelse fra arbeidsgiver"),
    I000066("Kopi av likningsattest eller selvangivelse"),
    I000067("Inntektsmelding"),

    I000107("Vurdering av arbeidsmulighet/sykmelding"),
    I000108("Opplysninger om muligheter og behov for tilrettelegging ved svangerskap"),
    I000109("Skjema for tilrettelegging og omplassering ved graviditet"),
    I000110("Dokumentasjon av aleneomsorg"),
    I000111("Dokumentasjon av begrunnelse for hvorfor man søker tilbake i tid"),
    I000112("Dokumentasjon av deltakelse i introduksjonsprogrammet"),
    I000114("Uttalelse tilbakekreving"),
    I000116("Bekreftelse på øvelse eller tjeneste i Forsvaret eller Sivilforsvaret"),
    I000117("Bekreftelse på tiltak i regi av Arbeids- og velferdsetaten"),

    I000118("Begrunnelse for sen søknad"),
    I000119("Uttalelse om tilbakebetaling"),
    I000120("Dokumentasjon på at mor er innlagt på sykehus"),
    I000121("Dokumentasjon på at mor er syk"),
    I000122("Dokumentasjon på at far/medmor er innlagt på sykehus"),
    I000123("Dokumentasjon på at far/medmor er syk"),
    I000124("Dokumentasjon på at barnet er innlagt på sykehus"),
    I000130("Dokumentasjon på at mor studerer og arbeider til sammen heltid"),
    I000131("Dokumentasjon på at mor studerer på heltid"),
    I000132("Dokumentasjon på at mor er i arbeid"),
    I000133("Dokumentasjon av mors deltakelse i kvalifiseringsprogrammet"),
    I000140("Skattemelding"),
    I000141("Terminbekreftelse")
    ;

    private final String beskrivelse;

    DokumentType(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }
}
