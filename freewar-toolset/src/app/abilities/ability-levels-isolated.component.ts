import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { Ability } from './ability'
import { AbilityDetailLevel } from './ability-detail-level'
import { AbilityDetailCell } from './ability-detail-cell'
import { AbilityService } from './ability.service';
import { Delay } from '../date/delay';
import { DelayService } from '../date/delay.service';

@Component({
  selector: 'fwts-ability-levels-isolated',
  templateUrl: './ability-levels-isolated.component.html',
  providers: [AbilityService, DelayService]
})
export class AbilityLevelsIsolatedComponent implements OnInit, OnChanges {

  detailedTableColumLabels: number[];
  detailedTableColums: AbilityDetailLevel[];

  @Input() ability: Ability;


  constructor(private abilityService: AbilityService, private delayService: DelayService) { }

  ngOnInit(): void {

    const detailedTableColumLabels: number[] = Array();
    for (let learningTechniqueLevel = 0; learningTechniqueLevel <= 50; learningTechniqueLevel += 5) {
      detailedTableColumLabels.push(learningTechniqueLevel);
    }
    this.detailedTableColumLabels = detailedTableColumLabels;

    return;
  }

  ngOnChanges(changes: any) {

    if (!this.ability) {
      return;
    }

    const detailedTableColums: AbilityDetailLevel[] = new Array<AbilityDetailLevel>();

    for (let abilityLevel = 1; abilityLevel <= this.ability.maxLevel; abilityLevel++) {

      const abilityDetailRow = new AbilityDetailLevel();
      abilityDetailRow.cells = new Array<AbilityDetailCell>();
      detailedTableColums.push(abilityDetailRow);

      abilityDetailRow.level = abilityLevel;

      for (let learningTechniqueLevel = 0; learningTechniqueLevel <= 50; learningTechniqueLevel += 5) {

        const abilityDetailCell = new AbilityDetailCell();
        abilityDetailRow.cells.push(abilityDetailCell);

        abilityDetailCell.learningTechniqueLevel = learningTechniqueLevel;
        const fromLevel = abilityLevel - 1;
        const toLevel = abilityLevel;
        const neededSeconds = this.abilityService.calculateUpgradeTime(this.ability, fromLevel, toLevel, learningTechniqueLevel);
        abilityDetailCell.neededTime = this.delayService.convertToDelay(neededSeconds);
      }

    }

    this.detailedTableColums = detailedTableColums;
  }



}


