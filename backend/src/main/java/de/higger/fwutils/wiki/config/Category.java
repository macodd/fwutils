package de.higger.fwutils.wiki.config;

import java.util.List;

public class Category {
	private String location;
	private int skipLimit;
	private List<String> exclusions;

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public int getSkipLimit() {
		return skipLimit;
	}

	public void setSkipLimit(final int skipLimit) {
		this.skipLimit = skipLimit;
	}

	public List<String> getExclusions() {
		return exclusions;
	}

	public void setExclusions(final List<String> exclusions) {
		this.exclusions = exclusions;
	}

}
