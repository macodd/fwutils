import { Weapon } from '../weapon/weapon';
import { WeaponService } from '../weapon/weapon.service';

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-weapon-consultant',
  templateUrl: './weapon-consultant.component.html',
  styleUrls: ['./weapon-consultant.component.css']
})
export class WeaponConsultantComponent implements OnInit {

  offenceArms: Array<Weapon> = [];
  defenceArms: Array<Weapon> = [];

  constructor(private weaponService: WeaponService) { }

  ngOnInit() {
    this.weaponService.getOffenceArms().subscribe(offenceArms => {
      this.offenceArms = offenceArms;
    });
    this.weaponService.getDefenceArms().subscribe(defenceArms => {
      this.defenceArms = defenceArms;
    });
  }

}
