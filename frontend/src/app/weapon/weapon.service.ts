import { Weapon } from './weapon';
import { WeaponType } from './weapontype';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

const DEFENCE_ARMS_ENDPOINT = 'http://localhost:9000/api/v1/public/defence-arms';
const OFFENCE_ARMS_ENDPOINT = 'http://localhost:9000/api/v1/public/offence-arms';

@Injectable()
export class WeaponService {

  constructor(private http: HttpClient) { }

  getDefenceArms(): Observable<Array<Weapon>> {

    return this.http.get<Array<Weapon>>(DEFENCE_ARMS_ENDPOINT).map(weapons => {
      this.fillWeaponType(weapons, WeaponType.DEFENCE_ARM);
      return weapons;
    });
  }

  getOffenceArms(): Observable<Array<Weapon>> {

    return this.http.get<Array<Weapon>>(OFFENCE_ARMS_ENDPOINT).map(weapons => {
      this.fillWeaponType(weapons, WeaponType.OFFENCE_ARM);
      return weapons;
    });
  }

  private fillWeaponType(weapons: Array<Weapon>, weaponType: WeaponType): void {
    weapons.forEach(weapon => {
      weapon.weaponType = weaponType;
    });
  }
}
