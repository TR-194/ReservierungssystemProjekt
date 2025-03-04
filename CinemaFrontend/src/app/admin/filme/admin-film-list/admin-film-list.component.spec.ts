import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFilmListComponent } from './admin-film-list.component';

describe('AdminFilmListComponent', () => {
  let component: AdminFilmListComponent;
  let fixture: ComponentFixture<AdminFilmListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminFilmListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFilmListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
