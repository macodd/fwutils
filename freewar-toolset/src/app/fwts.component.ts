import { Component, OnInit } from '@angular/core';
import { Ability } from './abilities/ability'
import { AbilityService } from './abilities/ability.service'


@Component({
  selector: 'fwts-root',
  templateUrl: './fwts.component.html',
  styleUrls: ['./fwts.component.css'],
  providers: [AbilityService]
})
export class FwtsComponent implements OnInit {
  title = 'Freewar Toolset';
  selectedAbility: Ability = AbilityService.createEmptyAbility();
  availableAbilities: Ability[] = Array<Ability>();

  constructor(private abilityService: AbilityService) { }

  ngOnInit(): void {
    this.abilityService.getAbilities().then(abilities => {
      this.selectedAbility = null;
      this.availableAbilities = abilities;
    });
  }

  onSelect(ability: Ability): void {
    this.selectedAbility = ability;
  }

}
