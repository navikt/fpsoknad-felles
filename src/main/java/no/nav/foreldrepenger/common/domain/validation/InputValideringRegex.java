package no.nav.foreldrepenger.common.domain.validation;

public class InputValideringRegex {

    public static final String FRITEKST = "^[\\p{L}\\p{N}\\p{Sc}\\p{Space}…'/%§&@_:;,\\.\\!\\?\\(\\)\\+\\=\\-\\–\\*\"]*$";
    public static final String FRITEKST_I_JSON_STRING = "^[\\p{L}\\p{N}\\p{Sc}\\p{Space}…'/%§&@_:;,\\.\\!\\?\\(\\[\\{\\}\\]\\)\\+\\=\\-\\–\\*\"]*$";

    private InputValideringRegex() {
        throw new IllegalAccessError("Skal ikke instansieres");
    }
}
