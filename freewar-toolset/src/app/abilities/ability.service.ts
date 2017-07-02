import { Injectable } from '@angular/core';
import { Ability } from './ability'

const ABILITIES: Ability[] = [
  { id: 0, name: 'Jagd', maxLevel: 50, baseTrainingTime: 8120 }
  , { id: 0, name: 'Kochkunst', maxLevel: 80, baseTrainingTime: 7700 }
];

const LEARNING_TECHNIQUE_PERCENTAGE = 1;

@Injectable()
export class AbilityService {

  static createEmptyAbility(): Ability {
    return { id: 0, name: '', maxLevel: 0, baseTrainingTime: 0 };
  }

  static faculty(level: number): number {
    const n = level - 1;
    const result = (n * (n + 1)) / 2;

    return result;
  }

  getMaxLearningTechniqueLevel(): number {
    return 50;
  }

  getAbilities(): Promise<Ability[]> {
    // return Promise.resolve(ABILITIES);
    return new Promise(resolve => {
      // Simulate server latency with 0,887 second delay
      setTimeout(() => resolve(ABILITIES), 1500 * Math.random());
    });
  }

  calculateUpgradeTime(ability: Ability, currentLevel: number, targetLevel: number, learningTechniqueLevel: number): number {

    if (currentLevel < 0) {
      currentLevel = 0;
    }

    if (targetLevel < 0) {
      targetLevel = 0;
    }

    let result: number;
    if (targetLevel - currentLevel > 0) {
      result = (AbilityService.faculty(targetLevel) - AbilityService.faculty(currentLevel)) * ability.baseTrainingTime;

      const lerningTechniqueValue = (100 - LEARNING_TECHNIQUE_PERCENTAGE) / 100;
      result = result * Math.pow(lerningTechniqueValue, learningTechniqueLevel);

    } else {
      result = 0;
    }

    return Math.floor(result);
  }

}
