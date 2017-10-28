package de.higger.fwutils.wiki.parse;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Hyperlink {

	private final String label;

	private final String relativeLocation;

	public Hyperlink(final String label, final String relativeLocation) {
		this.label = label;
		this.relativeLocation = relativeLocation;
	}

	public String getLabel() {
		return label;
	}

	public String getRelativeLocation() {
		return relativeLocation;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
