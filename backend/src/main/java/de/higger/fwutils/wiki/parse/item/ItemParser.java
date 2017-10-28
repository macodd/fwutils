package de.higger.fwutils.wiki.parse.item;

import org.jsoup.nodes.Document;

import de.higger.fwutils.wiki.exception.ItemParseException;
import de.higger.fwutils.wiki.parse.Hyperlink;

public interface ItemParser<T extends ParseItem> {

	T parseItem(Hyperlink hyperlink, Document document) throws ItemParseException;
}
