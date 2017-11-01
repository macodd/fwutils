import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeaponConsultantComponent } from './weapon-consultant.component';

describe('WeaponConsultantComponent', () => {
  let component: WeaponConsultantComponent;
  let fixture: ComponentFixture<WeaponConsultantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeaponConsultantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeaponConsultantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
