package de.higger.fwutils.npc.repository;

import org.springframework.data.repository.CrudRepository;

import de.higger.fwutils.npc.entity.NPC;

public interface NPCRepository extends CrudRepository<NPC, Long> {
}
