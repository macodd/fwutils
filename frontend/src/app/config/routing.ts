import { AbilityCalculatorComponent } from '../ability-calculator/ability-calculator.component';
import { FightEmulatorComponent } from '../fight-emulator/fight-emulator.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { WeaponConsultantComponent } from '../weapon-consultant/weapon-consultant.component';

import { Routes } from '@angular/router';

export const appRoutes: Routes = [
  { path: 'ability-calculator', component: AbilityCalculatorComponent },
  { path: 'weapon-consultant', component: WeaponConsultantComponent },
  { path: 'fight-emulator', component: FightEmulatorComponent },
  { path: '', redirectTo: '/error', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];
