import { appRoutes } from './config/routing';

import { AppComponent } from './app.component';
import { AbilityCalculatorComponent } from './ability-calculator/ability-calculator.component';
import { AbilityService } from './ability/ability.service';
import { SortAbilitiesPipe } from './ability/sort-abilities.pipe';
import { AppMaterialModule } from './config/app-material.module';
import { EnemyService } from './enemy/enemy.service';
import { FightEmulatorComponent } from './fight-emulator/fight-emulator.component';
import { MenuComponent } from './menu/menu.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { TemplateComponent } from './template/template.component';
import { WeaponService } from './weapon/weapon.service';
import { WeaponConsultantComponent } from './weapon-consultant/weapon-consultant.component';

import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormBuilder } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { NgDragDropModule } from 'ng-drag-drop';


@NgModule({
  imports: [
    AppMaterialModule,
    BrowserModule,
    NgDragDropModule.forRoot(),
    FlexLayoutModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  providers: [
    AbilityService,
    EnemyService,
    WeaponService,
    FormBuilder
  ],
  declarations: [
    AppComponent,
    MenuComponent,
    PageNotFoundComponent,
    TemplateComponent,
    AbilityCalculatorComponent,
    WeaponConsultantComponent,
    FightEmulatorComponent,
    SortAbilitiesPipe
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
