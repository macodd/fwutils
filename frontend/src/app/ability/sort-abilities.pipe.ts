import { Ability } from './ability';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortAbilities',
  pure: false
})
export class SortAbilitiesPipe implements PipeTransform {

  transform(abilities: Array<Ability>, args?: any): any {
    return abilities.sort((a1: Ability, a2: Ability) => {
      return a1.name.localeCompare(a2.name);
    });
  }

}
