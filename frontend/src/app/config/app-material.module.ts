import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatChipsModule,
  MatGridListModule,
  MatIconModule,
  MatMenuModule,
  MatProgressSpinnerModule,
  MatSidenavModule,
  MatStepperModule
} from '@angular/material';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    MatButtonModule,
    MatChipsModule,
    MatGridListModule,
    MatIconModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatStepperModule
  ],
  exports: [
    BrowserAnimationsModule,
    MatButtonModule,
    MatChipsModule,
    MatGridListModule,
    MatIconModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatStepperModule
  ]
})
export class AppMaterialModule {
}
