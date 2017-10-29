package de.higger.fwutils.wiki.parse;

import org.jsoup.nodes.Document;

import de.higger.fwutils.wiki.exception.ItemParseException;

public interface ItemParser<T extends ParseItem> {

	T parseItem(Hyperlink hyperlink, Document document) throws ItemParseException;
}
