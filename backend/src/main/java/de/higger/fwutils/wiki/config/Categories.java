package de.higger.fwutils.wiki.config;

public class Categories {

	private Category npcs;
	private Category abilities;
	private Category offenceArms;
	private Category defenceArms;

	public Category getNpcs() {
		return npcs;
	}

	public void setNpcs(final Category npcs) {
		this.npcs = npcs;
	}

	public Category getAbilities() {
		return abilities;
	}

	public void setAbilities(final Category abilities) {
		this.abilities = abilities;
	}

	public Category getOffenceArms() {
		return this.offenceArms;
	}

	public void setOffenceArms(final Category offenceArms) {
		this.offenceArms = offenceArms;
	}

	public Category getDefenceArms() {
		return defenceArms;
	}

	public void setDefenceArms(final Category defenceArms) {
		this.defenceArms = defenceArms;
	}

}
