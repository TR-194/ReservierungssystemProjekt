import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFilmFormComponent } from './admin-film-form.component';

describe('AdminFilmFormComponent', () => {
  let component: AdminFilmFormComponent;
  let fixture: ComponentFixture<AdminFilmFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminFilmFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFilmFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
