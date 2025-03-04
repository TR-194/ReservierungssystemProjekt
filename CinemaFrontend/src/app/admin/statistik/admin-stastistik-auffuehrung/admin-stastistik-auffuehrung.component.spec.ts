import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminStastistikAuffuehrungComponent } from './admin-stastistik-auffuehrung.component';

describe('AdminStastistikAuffuehrungComponent', () => {
  let component: AdminStastistikAuffuehrungComponent;
  let fixture: ComponentFixture<AdminStastistikAuffuehrungComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminStastistikAuffuehrungComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminStastistikAuffuehrungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
