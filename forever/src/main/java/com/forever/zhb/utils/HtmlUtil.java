package com.forever.zhb.utils;

public class HtmlUtil {

    private static String filterHtml(String content) {
        return content.replaceAll("<br />", "<br></br>")
            .replaceAll("&nbsp;", "　")
            .replaceAll("&quot;", "\"")
            .replaceAll("&ldquo;", "“")
            .replaceAll("&rdquo;", "”")
            .replaceAll("&middot;", "·")
            .replaceAll("&lt;", "<")
            .replaceAll("&frasl;", "/")
            .replaceAll("&acute;", "´")
            .replaceAll("&amp;", "&")
            .replaceAll("&bull;", "•")
            .replaceAll("&cedil;", "¸")
            .replaceAll("&copy;", "©")
            .replaceAll("&deg;", "°")
            .replaceAll("&divide;", "÷")
            .replaceAll("&frac12;", "½")
            .replaceAll("&frac14;", "¼")
            .replaceAll("&iexcl;", "¡")
            .replaceAll("&iquest;", "¿")
            .replaceAll("&laquo;", "«")
            .replaceAll("&gt;", ">")
            .replaceAll("&macr;", "¯")
            .replaceAll("&micro;", "µ")
            .replaceAll("&not;", "¬")
            .replaceAll("&para;", "¶")
            .replaceAll("&plusmn;", "±")
            .replaceAll("&reg;", "®")
            .replaceAll("&raquo;", "»")
            .replaceAll("&sect;", "§")
            .replaceAll("&uml;", "¨")
            .replaceAll("&times;", "×")
            .replaceAll("&trade;", "™")

            .replaceAll("&bdquo;", "„")
            .replaceAll("&circ;", "ˆ")
            .replaceAll("&dagger;", "†")
            .replaceAll("&Dagger;", "‡")

            .replaceAll("&hellip;", "…")
            .replaceAll("&lsaquo;", "‹")
            .replaceAll("&lsquo;", "‘")

            .replaceAll("&mdash;", "—")
            .replaceAll("&ndash;", "–")
            .replaceAll("&permil;", "‰")

            .replaceAll("&rsaquo;", "›")
            .replaceAll("&rsquo;", "’")
            .replaceAll("&sbquo;", "‚")
            .replaceAll("&shy;", " ")

            .replaceAll("&rsaquo;", "›")
            .replaceAll("&rsquo;", "’")
            .replaceAll("&sbquo;", "‚")
            ;

    }

}
