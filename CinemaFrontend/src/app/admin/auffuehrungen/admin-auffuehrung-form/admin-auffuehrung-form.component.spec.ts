import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAuffuehrungFormComponent } from './admin-auffuehrung-form.component';

describe('AdminAuffuehrungFormComponent', () => {
  let component: AdminAuffuehrungFormComponent;
  let fixture: ComponentFixture<AdminAuffuehrungFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAuffuehrungFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAuffuehrungFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
