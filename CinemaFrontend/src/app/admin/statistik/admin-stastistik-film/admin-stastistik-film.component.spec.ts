import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminStatistikFilmComponent } from './admin-stastistik-film.component';

describe('AdminStatistikFilmComponent', () => {
  let component: AdminStatistikFilmComponent;
  let fixture: ComponentFixture<AdminStatistikFilmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminStatistikFilmComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminStatistikFilmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
