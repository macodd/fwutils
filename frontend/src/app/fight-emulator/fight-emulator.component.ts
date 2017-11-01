import { Enemy } from '../enemy/enemy';
import { EnemyService } from '../enemy/enemy.service';

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fight-emulator',
  templateUrl: './fight-emulator.component.html',
  styleUrls: ['./fight-emulator.component.css']
})
export class FightEmulatorComponent implements OnInit {

  npcs: Array<Enemy> = [];

  constructor(private enemyService: EnemyService) { }

  ngOnInit() {
    this.enemyService.getNPCs().subscribe((npcs: Array<Enemy>) => {
      this.npcs = npcs;
    });
  }

}
