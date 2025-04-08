package main.Parser;

import main.Dto.FlatDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Pattern pattern;

    public static String parseLinks(String in) {
        pattern = Pattern.compile("/detail/prodej/byt[^\"]+");
        StringBuilder content = new StringBuilder();
        Matcher matcher = pattern.matcher(in);
        while (matcher.find()) {
            content.append("https://www.sreality.cz").append(matcher.group()).append("\n");
        }
        return content.toString();
    }

    public static FlatDto parseFlatDto(String in) {
        return new FlatDto(getMetraz(in), getRozloha(in), getEnergetickaNarocnost(in), getStav(in), getLokalita(in), getCena(in));
    }

    private static int getMetraz(String in) {
        pattern = Pattern.compile("css-e2su9m\">Užitná plocha (\\d+) m²</div>");
        Matcher matcher = pattern.matcher(in);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    private static String getRozloha(String in) {
        pattern = Pattern.compile("(\\d\\+kk)</a></li><li aria-hidden=\"true\" class=\"MuiBreadcrumbs-separator css-1nd5pgh");
        Matcher matcher = pattern.matcher(in);
        matcher.find();
        return matcher.group(1);

    }

    private static String getEnergetickaNarocnost(String in) {
        pattern = Pattern.compile("css-sdwmvq\" aria-hidden=\"true\">([A-Z])</p><style data-emotion=\"css 1sdpd03");
        Matcher matcher = pattern.matcher(in);
        matcher.find();
        return matcher.group(1);
    }

    private static String getStav(String in) {
        pattern = Pattern.compile("Stavba<!-- -->:</dt><dd class=\"MuiTypography-root MuiTypography-body1 css-1gye5tz\">[A-Za-zěščřžýáí, ]*(Ve velmi dobrém stavu|V dobrém stavu|Ve špatném stavu|Novostavba|Po rekonstrukci),");
        Matcher matcher = pattern.matcher(in);
        matcher.find();
        return matcher.group(1);
    }

    private static String getLokalita(String in) {
        pattern = Pattern.compile("/(jihocesky-kraj|jihomoravsky-kraj|karlovarsky-kraj|kralovehradecky-kraj|liberecky-kraj|moravskoslezsky-kraj|olomoucky-kraj|pardubicky-kraj|plzensky-kraj|praha|stredocesky-kraj|ustecky-kraj|vysocina-kraj|zlinsky-kraj)");
        Matcher matcher = pattern.matcher(in);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "neznamy-kraj";
    }

    private static int getCena(String in) {
        pattern = Pattern.compile("MuiTypography-body1 css-1b1ajfd\">([^K]+)K");
        Matcher matcher = pattern.matcher(in);
        matcher.find();
        return Integer.parseInt(matcher.group(1).replaceAll("\\D+", ""));
    }
}
