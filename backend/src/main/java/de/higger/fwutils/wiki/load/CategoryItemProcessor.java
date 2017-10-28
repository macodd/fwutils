package de.higger.fwutils.wiki.load;

import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.Assert;

import de.higger.fwutils.wiki.parse.CategoryParser;
import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.item.ItemParser;
import de.higger.fwutils.wiki.parse.item.ParseItem;

public class CategoryItemProcessor implements ItemProcessor<Hyperlink, ParseItem> {

	private final CategoryParser categoryParser;
	private final ItemParser<? extends ParseItem> itemParser;

	public CategoryItemProcessor(final CategoryParser categoryParser,
			final ItemParser<? extends ParseItem> itemParser) {
		Assert.notNull(categoryParser, "A CategoryParser is required.");
		Assert.notNull(itemParser, "A ItemParser is required.");

		this.categoryParser = categoryParser;
		this.itemParser = itemParser;
	}

	@Override
	public ParseItem process(final Hyperlink item) throws Exception {

		final Document document = categoryParser.readWikiDocument(item.getRelativeLocation());
		final ParseItem parsedItem = itemParser.parseItem(item, document);

		return parsedItem;
	}

}
