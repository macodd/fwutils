package de.higger.fwutils.wiki.parse.item;

public class AbilityItem extends ParseItem {

	private static final long serialVersionUID = -1996378712210854830L;

	public AbilityItem(final String url) {
		super(url);
	}

	private Integer baseTime;
	private Short maxLevel;
	private String raceBonus;

	public Integer getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(final Integer baseTime) {
		this.baseTime = baseTime;
	}

	public Short getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(final Short maxLevel) {
		this.maxLevel = maxLevel;
	}

	public String getRaceBonus() {
		return raceBonus;
	}

	public void setRaceBonus(final String raceBonus) {
		this.raceBonus = raceBonus;
	}

}
