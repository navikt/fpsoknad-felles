package no.nav.foreldrepenger.common.domain.validation;

public class InputValideringRegex {

    private static final String ANFØRSELSTEGN = "\\u2018\\u2019\\u201a\\u201b\\u201c\\u201d\\u201e\\u201f";
    private static final String AKSENTTEGN = "\\u00b4";
    private static final String PUNKTTEGN = "\\u2026";

    public static final String BARE_TALL = "^[\\p{Digit}]*$";
    public static final String BARE_BOKSTAVER = "^[\\p{L}]*$";
    public static final String BOKSTAVER_MED_MELLOMROM = "^[\\p{L}\\p{Space}]*$";
    public static final String NORSK_FØDSELSNUMMER = "^[\\p{Digit}]{11}+$";
    public static final String ORGNUMMER = "^[\\p{Digit}]{9}+$";
    public static final String FRITEKST = "^[\\p{Punct}\\p{L}\\p{M}\\p{N}\\p{Sc}\\p{Space}«»–§�" + ANFØRSELSTEGN + AKSENTTEGN + PUNKTTEGN + "]*$";

    private InputValideringRegex() {
        throw new IllegalAccessError("Skal ikke instansieres");
    }
}
