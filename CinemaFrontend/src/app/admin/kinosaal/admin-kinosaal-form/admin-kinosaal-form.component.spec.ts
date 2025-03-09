import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminKinosaalFormComponent } from './admin-kinosaal-form.component';

describe('AdminKinosaalFormComponent', () => {
  let component: AdminKinosaalFormComponent;
  let fixture: ComponentFixture<AdminKinosaalFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminKinosaalFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminKinosaalFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
