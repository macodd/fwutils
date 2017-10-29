package de.higger.fwutils.npc.parse.item;

import de.higger.fwutils.wiki.parse.ParseItem;

public class NPC extends ParseItem {

	private static final long serialVersionUID = 3057239845992900635L;

	private String npcType;

	private Integer minStrength;
	private Integer maxStrength;

	private Integer minHealth;
	private Integer maxHealth;

	private Integer droppedMoney;

	public NPC(final String url) {
		super(url);
	}

	public String getNpcType() {
		return npcType;
	}

	public void setNpcType(final String npcType) {
		this.npcType = npcType;
	}

	public Integer getMinStrength() {
		return minStrength;
	}

	public void setMinStrength(final Integer minStrength) {
		this.minStrength = minStrength;
	}

	public Integer getMaxStrength() {
		return maxStrength;
	}

	public void setMaxStrength(final Integer maxStrength) {
		this.maxStrength = maxStrength;
	}

	public Integer getMinHealth() {
		return minHealth;
	}

	public void setMinHealth(final Integer minHealth) {
		this.minHealth = minHealth;
	}

	public Integer getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(final Integer maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Integer getDroppedMoney() {
		return droppedMoney;
	}

	public void setDroppedMoney(final Integer droppedMoney) {
		this.droppedMoney = droppedMoney;
	}

}
