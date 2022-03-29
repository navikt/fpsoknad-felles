package no.nav.foreldrepenger.common.domain.validation;

public class InputValideringRegex {

    private static final String START = "^[";
    private static final String BOKSTAVER_OG_TALL = "0-9a-zA-ZÁáĄąÂâĀāĂăßČčĆćÇçĎďĐđÐðĔĕÉéĘęĖėÈèËëÊêĒēĢģİiĮįÍíÎîÏïĪīĶķŁłŊŋŇňŃńŅņÑñÞþŠšŚśŞşŤťŦŧŢţŲųŪūÚúŮůÝýŽžŹźŻżÕõÔôÓóÖöÜüÄäŒœÆæØøÅå";
    // Følgende tegn må escapes med \\ i Java hvis de skal tillates (ellers kan de ha en annen betydning): <([{\\^-=$!|]})?*+.>
    private static final String FORVENTEDE_SPESIAL_TEGN_FRITEKST = " …'/%§&@_:;," + "\\.\\!\\?\\(\\)\\+\\=\\-\\–\\*\"\n\t"; // TODO: Tillat backslash '\' ?
    private static final String FORVENTEDE_SPESIAL_TEGN_JSON = "\\[\\{\\}\\]";
    private static final String SLUTT = "]*$";

    public static final String FRITEKST = START + BOKSTAVER_OG_TALL + FORVENTEDE_SPESIAL_TEGN_FRITEKST + SLUTT;
    public static final String FRITEKST_JSON = START + BOKSTAVER_OG_TALL + FORVENTEDE_SPESIAL_TEGN_FRITEKST + FORVENTEDE_SPESIAL_TEGN_JSON + SLUTT;

    private InputValideringRegex() {
        throw new IllegalAccessError("Skal ikke instansieres");
    }
}
