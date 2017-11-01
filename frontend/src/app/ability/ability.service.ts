import { Ability } from './ability';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

const ABILITY_ENDPOINT = 'http://localhost:9000/api/v1/public/abilities';

@Injectable()
export class AbilityService {

  constructor(private http: HttpClient) { }

  getAbilities(): Observable<Array<Ability>> {
    return this.http.get<Array<Ability>>(ABILITY_ENDPOINT);
  }
}
