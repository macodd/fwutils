package de.higger.fwutils.wiki.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.batch.core.SkipListener;

import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.ParseItem;

public class CategorySkipListener implements SkipListener<Hyperlink, ParseItem> {

	private static final Logger LOG = LoggerFactory.getLogger(CategorySkipListener.class);

	@Override
	public void onSkipInRead(final Throwable t) {
		LOG.warn("Failed to read category items cause", t);
	}

	@Override
	public void onSkipInProcess(final Hyperlink item, final Throwable t) {
		LOG.warn(format("Failed to parse item {} cause", item), t);

	}

	@Override
	public void onSkipInWrite(final ParseItem item, final Throwable t) {
		LOG.warn(format("Failed to write item {} cause", item), t);
	}

	public String format(final String format, final Object... args) {
		return MessageFormatter.arrayFormat(format, args).getMessage();
	}
}
