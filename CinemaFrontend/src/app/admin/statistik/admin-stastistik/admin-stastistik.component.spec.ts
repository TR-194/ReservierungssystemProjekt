import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminStastistikComponent } from './admin-stastistik.component';

describe('AdminStastistikComponent', () => {
  let component: AdminStastistikComponent;
  let fixture: ComponentFixture<AdminStastistikComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminStastistikComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminStastistikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
