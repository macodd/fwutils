import { Enemy } from './enemy';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';


const NPC_ENDPOINT = 'http://localhost:9000/api/v1/public/npcs';

@Injectable()
export class EnemyService {
  constructor(private http: HttpClient) { }

  getNPCs(): Observable<Array<Enemy>> {
    return this.http.get<Array<Enemy>>(NPC_ENDPOINT);
  }

}
