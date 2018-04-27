package com.forever.zhb.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {

	private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

	public static Document getDocumentByUrl(String url) {
		if (StringUtil.isBlank(url)) {
			return null;
		}
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("init document fail");
		}
		return document;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	public static void main(String[] args) {
		String url = "http://news.ycombinator.com";
		Document doc = JsoupUtil.getDocumentByUrl(url);
		if (null != doc) {
			Elements media = doc.select("[src]");
			Elements imports = doc.select("link[href]");
			Elements links = doc.select("a[href]");

			if (null != media) {
				print("\nMedia: (%d)", media.size());
				for (Element src : media) {
					if (src.tagName().equals("img"))
						print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
								src.attr("height"), trim(src.attr("alt"), 20));
					else
						print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
				}
			}

			if (null != imports) {
				print("\nImports: (%d)", imports.size());
				for (Element link : imports) {
					print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
				}
			}

			if (null != imports) {
				print("\nLinks: (%d)", links.size());
				for (Element link : links) {
					print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
				}
			}

		}

	}

}
