import { TestBed, async } from '@angular/core/testing';

import { FwtsComponent } from './fwts.component';
import { Ability } from './abilities/ability'
import { AbilityDetailLevel } from './abilities/ability-detail-level'
import { AbilityDetailCell } from './abilities/ability-detail-cell'
import { AbilityDetailComponent } from './abilities/ability-detail.component'
import { AbilityLevelsIsolatedComponent } from './abilities/ability-levels-isolated.component'
import { AbilityLevelsTotalComponent } from './abilities/ability-levels-total.component'
import { AbilityLevelSingleComponent } from './abilities/ability-level-single.component'
import { FormatDelayPipe } from './date/format-deplay.pipe';

describe('FwtsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        FwtsComponent
        , AbilityDetailComponent
        , AbilityLevelsIsolatedComponent
        , AbilityLevelsTotalComponent
        , AbilityLevelSingleComponent
        , FormatDelayPipe
      ],
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(FwtsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'app'`, async(() => {
    const fixture = TestBed.createComponent(FwtsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Freewar Toolset');
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(FwtsComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to Freewar Toolset!!');
  }));
});
