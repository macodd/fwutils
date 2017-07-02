import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { Ability } from './ability'
import { AbilityDetailLevel } from './ability-detail-level'
import { AbilityDetailCell } from './ability-detail-cell'
import { AbilityService } from './ability.service';
import { Delay } from '../date/delay';
import { DelayService } from '../date/delay.service';

@Component({
  selector: 'fwts-ability-levels-total',
  templateUrl: './ability-levels-total.component.html',
  providers: [AbilityService, DelayService]
})
export class AbilityLevelsTotalComponent implements OnInit, OnChanges {

  detailedTableColumLabels: number[];
  detailedTableColums: AbilityDetailLevel[];

  @Input() ability: Ability;

  constructor(private abilityService: AbilityService, private delayService: DelayService) { }

  ngOnInit(): void {

    const detailedTableColumLabels: number[] = Array();
    const maxLearingTechniqueLevel = this.abilityService.getMaxLearningTechniqueLevel();

    for (let learningTechniqueLevel = 0; learningTechniqueLevel <= maxLearingTechniqueLevel; learningTechniqueLevel += 5) {
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
    const maxLearingTechniqueLevel = this.abilityService.getMaxLearningTechniqueLevel();

    for (let abilityLevel = 5; abilityLevel <= this.ability.maxLevel; abilityLevel = abilityLevel + 5) {

      const abilityDetailRow = new AbilityDetailLevel();
      abilityDetailRow.cells = new Array<AbilityDetailCell>();
      detailedTableColums.push(abilityDetailRow);

      abilityDetailRow.level = abilityLevel;

      for (let learningTechniqueLevel = 0; learningTechniqueLevel <= maxLearingTechniqueLevel; learningTechniqueLevel += 5) {

        const abilityDetailCell = new AbilityDetailCell();
        abilityDetailRow.cells.push(abilityDetailCell);

        abilityDetailCell.learningTechniqueLevel = learningTechniqueLevel;
        const fromLevel = 1;
        const toLevel = abilityLevel;
        const neededSeconds = this.abilityService.calculateUpgradeTime(this.ability, fromLevel, toLevel, learningTechniqueLevel);
        abilityDetailCell.neededTime = this.delayService.convertToDelay(neededSeconds);
      }

    }

    this.detailedTableColums = detailedTableColums;
  }



}


