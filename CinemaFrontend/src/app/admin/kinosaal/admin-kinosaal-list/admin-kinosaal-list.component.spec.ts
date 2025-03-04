import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminKinosaalListComponent } from './admin-kinosaal-list.component';

describe('AdminKinosaalListComponent', () => {
  let component: AdminKinosaalListComponent;
  let fixture: ComponentFixture<AdminKinosaalListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminKinosaalListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminKinosaalListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
