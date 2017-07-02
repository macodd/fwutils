import { Component, Input } from '@angular/core';
import { Ability } from './ability'

@Component({
  selector: 'fwts-ability-detail',
  templateUrl: './ability-detail.component.html',
})
export class AbilityDetailComponent {

  @Input() ability: Ability;

}


