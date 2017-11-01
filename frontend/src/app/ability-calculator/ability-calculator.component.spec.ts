import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AbilityCalculatorComponent } from './ability-calculator.component';

describe('AbilityCalculatorComponent', () => {
  let component: AbilityCalculatorComponent;
  let fixture: ComponentFixture<AbilityCalculatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AbilityCalculatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AbilityCalculatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
