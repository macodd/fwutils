package de.higger.fwutils.ability.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "ability")
public class Ability {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_ability_seq")
	@SequenceGenerator(name = "id_ability_seq", sequenceName = "ability_seq")
	private long id;
	private String name;
	private String url;

	private Integer baseTime;
	private Short maxLevel;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Integer getBaseTime() {
		return baseTime;
	}

	public Short getMaxLevel() {
		return maxLevel;
	}

}
