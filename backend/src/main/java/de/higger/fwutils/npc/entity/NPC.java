package de.higger.fwutils.npc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "npc")
public class NPC {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_npc_seq")
	@SequenceGenerator(name = "id_npc_seq", sequenceName = "npc_seq")
	private long id;
	private String name;
	private String url;

	private Integer minStrength;
	private Integer maxStrength;
	private Integer minHealth;
	private Integer maxHealth;
	private Integer droppedMoney;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Integer getMinStrength() {
		return minStrength;
	}

	public Integer getMaxStrength() {
		return maxStrength;
	}

	public Integer getMinHealth() {
		return minHealth;
	}

	public Integer getMaxHealth() {
		return maxHealth;
	}

	public Integer getDroppedMoney() {
		return droppedMoney;
	}

}
