import { TestBed, inject } from '@angular/core/testing';

import { Ability } from './ability';
import { AbilityService } from './ability.service';
import { FormatDelayPipe } from '../date/format-deplay.pipe';

const MIN_TECHNIQUE_LEVEL = 0;
const MAX_TECHNIQUE_LEVEL = 50;

describe('When calculation upgrade time', () => {
  let abilityService: AbilityService;
  let anyAbility: Ability;

  beforeEach(() => {
    abilityService = new AbilityService();
    anyAbility = {id:0, name: '', maxLevel: 0, baseTrainingTime: 500};
  });

  it('calculates 0 for first upgrade with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 0, 1, MIN_TECHNIQUE_LEVEL)).toEqual(0);
  });

  it('calculates 0 for first upgrade with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 0, 1, MAX_TECHNIQUE_LEVEL)).toEqual(0);
  });

  it('calculates 500 for upgrade from 1 to 2 with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 2, MIN_TECHNIQUE_LEVEL)).toEqual(500);
  });

  it('calculates 302 for upgrade from 1 to 2 with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 2, MAX_TECHNIQUE_LEVEL)).toEqual(302);
  });

  it('calculates 1000 for upgrade from 2 to 3 with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 2, 3, MIN_TECHNIQUE_LEVEL)).toEqual(1000);
  });

  it('calculates 605 for upgrade from 2 to 3 with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 2, 3, MAX_TECHNIQUE_LEVEL)).toEqual(605);
  });

  it('calculates 1500 for upgrade from 1 to 3 with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 3, MIN_TECHNIQUE_LEVEL)).toEqual(1500);
  });

  it('calculates 907 for upgrade from 1 to 3 with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 3, MAX_TECHNIQUE_LEVEL)).toEqual(907);
  });

  it('calculates 1500 for upgrade from 3 to 4 with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 3, 4, MIN_TECHNIQUE_LEVEL)).toEqual(1500);
  });

  it('calculates 907 for upgrade from 3 to 4 with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 3, 4, MAX_TECHNIQUE_LEVEL)).toEqual(907);
  });

  it('calculates 3000 for upgrade from 1 to 4 with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 4, MIN_TECHNIQUE_LEVEL)).toEqual(3000);
  });

  it('calculates 1815 for upgrade from 1 to 4 with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 1, 4, MAX_TECHNIQUE_LEVEL)).toEqual(1815);
  });


  it('calculates 0 for no upgrade with min technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 4, 4, MIN_TECHNIQUE_LEVEL)).toEqual(0);
  });

  it('calculates 0 for no upgrade with max technique level', () => {
    expect(abilityService.calculateUpgradeTime(anyAbility, 4, 4, MAX_TECHNIQUE_LEVEL)).toEqual(0);
  });
});

describe('AbilityService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AbilityService]
    });
  });

  it('should be created', inject([AbilityService], (service: AbilityService) => {
    expect(service).toBeTruthy();
  }));
});
