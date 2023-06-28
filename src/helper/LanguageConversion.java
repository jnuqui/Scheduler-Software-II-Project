package helper;


import java.util.Locale;

public class LanguageConversion
{
    public static void changeToFrench() {
        Locale france = new Locale("fr", "FR");
        Locale.setDefault(france);

    }
}
