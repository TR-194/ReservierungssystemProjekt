import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SitzplatzComponent } from './sitzplatz.component';

describe('SitzplatzComponent', () => {
  let component: SitzplatzComponent;
  let fixture: ComponentFixture<SitzplatzComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SitzplatzComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SitzplatzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
