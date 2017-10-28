package de.higger.fwutils.wiki.parse;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.higger.fwutils.wiki.config.ParserConfiguration;

@Component
public class DocumentParser {

	@Autowired
	private ParserConfiguration parserConfiguration;

	public Document getDocument(final String uri) throws IOException {

		final String userAgent = parserConfiguration.getUserAgent();
		final int timeout = parserConfiguration.getTimeout();

		final Document doc = Jsoup.connect(uri).userAgent(userAgent).timeout(timeout).get();

		return doc;
	}
}
