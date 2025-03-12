import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservierungFormComponent } from './reservierung-form.component';

describe('ReservierungFormComponent', () => {
  let component: ReservierungFormComponent;
  let fixture: ComponentFixture<ReservierungFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservierungFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservierungFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
