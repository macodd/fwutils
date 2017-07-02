import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { Ability } from './ability'
import { Delay } from '../date/delay'
import { DelayService } from '../date/delay.service'
import { AbilityService } from './ability.service'

@Component({
  selector: 'fwts-ability-level-single',
  templateUrl: './ability-level-single.component.html',
  providers: [DelayService, AbilityService]
})
export class AbilityLevelSingleComponent implements OnInit, OnChanges {

  @Input() ability: Ability;

  abilityLevels: number[] = Array();
  learingTechniqueLevels: number[] = Array();

  selectedAbilityLevel = 0;
  selectedLearingTechniqueLevel = 0;

  totalTimeNeededDelay: Delay;
  savedTimeDelay: Delay;

  constructor(private abilityService: AbilityService, private delayService: DelayService) { }

  ngOnInit(): void {

    const maxLearingTechniqueLevel = this.abilityService.getMaxLearningTechniqueLevel();
    const learingTechniqueLevels = new Array(maxLearingTechniqueLevel + 1);

    for (let i = 0; i <= maxLearingTechniqueLevel; i++) {
      learingTechniqueLevels[i] = i;
    }

    this.learingTechniqueLevels = learingTechniqueLevels;

    this.updateNeededTime();
    return;
  }

  ngOnChanges(changes: any): void {

    const abilityLevels = new Array(this.ability.maxLevel + 1);

    for (let i = 0; i <= this.ability.maxLevel; i++) {
      abilityLevels[i] = i;
    }

    this.abilityLevels = abilityLevels;

    return;
  }

  onChangeAbilityLevel(selectedAbilityLevel: number): void {
    this.selectedAbilityLevel = selectedAbilityLevel;
    this.updateNeededTime();
  }
  onChangeLearningTechniqueLevel(selectedLearingTechniqueLevel: number): void {
    this.selectedLearingTechniqueLevel = selectedLearingTechniqueLevel;
    this.updateNeededTime();
  }

  updateNeededTime(): void {
    const neededSeconds = this.abilityService.calculateUpgradeTime(this.ability, 0, this.selectedAbilityLevel,
      this.selectedLearingTechniqueLevel);
    const neededSecondsNoLT = this.abilityService.calculateUpgradeTime(this.ability, 0, this.selectedAbilityLevel, 0);

    this.totalTimeNeededDelay = this.delayService.convertToDelay(neededSeconds);
    this.savedTimeDelay = this.delayService.convertToDelay(neededSecondsNoLT - neededSeconds);
  };


}
