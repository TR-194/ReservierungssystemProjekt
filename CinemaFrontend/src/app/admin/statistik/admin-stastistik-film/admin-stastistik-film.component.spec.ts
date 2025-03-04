import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminStastistikFilmComponent } from './admin-stastistik-film.component';

describe('AdminStastistikFilmComponent', () => {
  let component: AdminStastistikFilmComponent;
  let fixture: ComponentFixture<AdminStastistikFilmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminStastistikFilmComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminStastistikFilmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
