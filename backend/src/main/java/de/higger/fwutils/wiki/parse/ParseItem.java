package de.higger.fwutils.wiki.parse;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class ParseItem implements Serializable {

	private static final long serialVersionUID = 575664160353998762L;

	private String label;
	private final String url;

	public ParseItem(final String url) {
		this.url = url;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
