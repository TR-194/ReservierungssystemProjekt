import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuffuehrungDetailComponent } from './auffuehrung-detail.component';

describe('AuffuehrungDetailComponent', () => {
  let component: AuffuehrungDetailComponent;
  let fixture: ComponentFixture<AuffuehrungDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuffuehrungDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuffuehrungDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
