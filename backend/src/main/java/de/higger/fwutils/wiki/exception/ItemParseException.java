package de.higger.fwutils.wiki.exception;

import de.higger.fwutils.wiki.parse.Hyperlink;

public class ItemParseException extends Exception {

	private static final long serialVersionUID = 548553167503741978L;

	public ItemParseException(final String type, final Hyperlink source) {
		super(String.format("Failed to parse %s from %s ", type, source.toString()));
	}
}
