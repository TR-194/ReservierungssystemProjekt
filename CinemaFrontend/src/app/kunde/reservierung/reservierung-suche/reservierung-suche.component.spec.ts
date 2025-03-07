import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservierungSucheComponent } from './reservierung-suche.component';

describe('ReservierungSucheComponent', () => {
  let component: ReservierungSucheComponent;
  let fixture: ComponentFixture<ReservierungSucheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservierungSucheComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservierungSucheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
