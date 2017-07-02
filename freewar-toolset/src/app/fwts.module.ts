import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FwtsComponent } from './fwts.component';
import { AbilityDetailComponent } from './abilities/ability-detail.component';
import { AbilityLevelsIsolatedComponent } from './abilities/ability-levels-isolated.component';
import { AbilityLevelsTotalComponent } from './abilities/ability-levels-total.component';
import { AbilityLevelSingleComponent } from './abilities/ability-level-single.component';
import { FormatDelayPipe } from './date/format-deplay.pipe';


@NgModule({
  declarations: [
    FwtsComponent
    , AbilityDetailComponent
    , AbilityLevelsIsolatedComponent
    , AbilityLevelsTotalComponent
    , AbilityLevelSingleComponent
    , FormatDelayPipe, AbilityLevelSingleComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [FwtsComponent]
})
export class FwtsModule { }
