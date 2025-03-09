import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAuffuehrungListComponent } from './admin-auffuehrung-list.component';

describe('AdminAuffuehrungListComponent', () => {
  let component: AdminAuffuehrungListComponent;
  let fixture: ComponentFixture<AdminAuffuehrungListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAuffuehrungListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAuffuehrungListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
