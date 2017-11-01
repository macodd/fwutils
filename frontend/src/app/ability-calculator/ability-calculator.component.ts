import { Ability } from '../ability/ability';
import { AbilityService } from '../ability/ability.service';

import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-ability-calculator',
  templateUrl: './ability-calculator.component.html',
  styleUrls: ['./ability-calculator.component.css'],
})
export class AbilityCalculatorComponent implements OnInit {

  abilities: Array<Ability> = [];

  chooseAbilitiesFormGroup: FormGroup;

  constructor(private abilityService: AbilityService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.abilityService.getAbilities().subscribe((abilities: Array<Ability>) => {
      this.abilities = abilities;

      this.chooseAbilitiesFormGroup = this.formBuilder.group({
        'chosenAbilities': new FormControl([], this.minChosenAbilities(1)),
        'notChosenAbilities': new FormControl(abilities, [])
      });
    });

  }

  get chosenAbilities(): Array<Ability> {
    return this.chooseAbilitiesFormGroup.get('chosenAbilities').value;
  }

  get notChosenAbilities(): Array<Ability> {
    return this.chooseAbilitiesFormGroup.get('notChosenAbilities').value;
  }


  public onDropToChosenAbilities(dropEvent: any): void {
    const chosenAbility: Ability = dropEvent.dragData;
    this.moveFromArrayToArray(chosenAbility, this.notChosenAbilities, this.chosenAbilities);
    this.flushValidatorOnChooseAbilitiesFormGroup();

  }

  public onRemoveFromChosenAbilities(ability: Ability): void {
    this.moveFromArrayToArray(ability, this.chosenAbilities, this.notChosenAbilities);
    this.flushValidatorOnChooseAbilitiesFormGroup();
  }

  private minChosenAbilities(expectedMinValues: number): ValidatorFn {
    return (c: AbstractControl): { [key: string]: any } => {
      console.log(c.value);
      if (c.value.length >= expectedMinValues) {
        return null;
      }

      return { 'minChosenAbilities': { valid: false } };
    };
  }


  private moveFromArrayToArray(item: Ability, source: Array<Ability>, target: Array<Ability>): void {
    if (target.indexOf(item) < 0) {
      target.push(item);
    }

    const arrayIndex = source.indexOf(item);
    if (arrayIndex >= 0) {
      source.splice(arrayIndex, 1);
    }
  }

  private flushValidatorOnChooseAbilitiesFormGroup(): void {
    this.chooseAbilitiesFormGroup.setValue({
      'chosenAbilities': this.chosenAbilities,
      'notChosenAbilities': this.notChosenAbilities
    });
  }

}
