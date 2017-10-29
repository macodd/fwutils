package de.higger.fwutils.ability.repository;

import org.springframework.data.repository.CrudRepository;

import de.higger.fwutils.ability.entity.Ability;

public interface AbilityRepository extends CrudRepository<Ability, Long> {
}
