package de.higger.fwutils.wiki.parse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.higger.fwutils.wiki.config.WikiConfiguration;

@Component
public class CategoryParser {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryParser.class);

	private static final String TAG_LINK = "a";
	private static final String ATTRIBUTE_LINK_HREF = "href";
	private static final String LABEL_LINK_NEXT_PAGE = "nächste";

	@Autowired
	private DocumentParser documentParser;

	@Autowired
	private WikiConfiguration wikiConfiguration;

	public List<Hyperlink> getItemsOfCategory(final String categoryURL,
			final Predicate<? super Hyperlink> filterItems) {

		final List<Hyperlink> itemLinks = new LinkedList<>();

		scanLinksRecursive(categoryURL, itemLinks);

		final List<Hyperlink> categoryItems = itemLinks.stream().filter(filterItems)
				.sorted((h1, h2) -> h1.getLabel().compareTo(h2.getLabel())).collect(Collectors.toList());

		LOG.info("Found {} items in category {}", categoryItems.size(), categoryURL);

		return categoryItems;
	}

	private void scanLinksRecursive(final String relativeCategoryLocation, final List<Hyperlink> itemLinks) {

		try {
			final Document document = readWikiDocument(relativeCategoryLocation);

			final Element pagesElement = document.select("div#mw-pages").first();

			final Elements categoryLinks = pagesElement.select("ul li a");
			final Elements navigationLinks = pagesElement.select(TAG_LINK);
			navigationLinks.removeAll(categoryLinks);

			for (final Element element : categoryLinks) {
				final String relativeLocation = element.attr(ATTRIBUTE_LINK_HREF);
				final String label = element.text();

				final Hyperlink link = new Hyperlink(label, relativeLocation);
				itemLinks.add(link);
			}

			for (final Element element : navigationLinks) {
				if (element.text().contains(LABEL_LINK_NEXT_PAGE)) {
					final String nextPage = element.attr(ATTRIBUTE_LINK_HREF);
					scanLinksRecursive(nextPage, itemLinks);
					break;
				}
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Document readWikiDocument(final String documentLocation) throws IOException {

		final String categoryURI = formatURL(wikiConfiguration.getUrl(), documentLocation);
		final Document document = documentParser.getDocument(categoryURI);

		return document;
	}

	private String formatURL(final String rootLocation, final String relativeCategoryURL) {
		final String url = String.format("%s%s", rootLocation, relativeCategoryURL);
		return url;
	}

}
