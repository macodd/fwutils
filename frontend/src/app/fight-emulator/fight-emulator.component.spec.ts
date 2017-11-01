import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FightEmulatorComponent } from './fight-emulator.component';

describe('FightEmulatorComponent', () => {
  let component: FightEmulatorComponent;
  let fixture: ComponentFixture<FightEmulatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FightEmulatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FightEmulatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
