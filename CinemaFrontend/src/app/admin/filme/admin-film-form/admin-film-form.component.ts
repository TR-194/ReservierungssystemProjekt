import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FilmService } from 'src/app/shared/services/film.service';
import { Film } from 'src/app/shared/models/film.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-admin-film-form',
    imports: [
      CommonModule,
      FormsModule
    ],
  templateUrl: './admin-film-form.component.html',
  styleUrls: ['./admin-film-form.component.css']
})
export class AdminFilmFormComponent {
  film: Film = { id: 0, titel: '', genre: '', dauer: 0 };
  filmId?: number;

  constructor(
    private filmService: FilmService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.filmId = this.route.snapshot.params['id'];
    if (this.filmId) {
      this.filmService.getFilmById(this.filmId).subscribe(
        (data: Film) => this.film = data,
        error => console.error('Fehler beim Laden des Films', error)
      );
    }
  }

  speichereFilm(): void {
    if (this.filmId) {
      this.filmService.bearbeiteFilm(this.filmId, this.film).subscribe(
        () => this.router.navigate(['/admin/filme']),
        error => console.error('Fehler beim Aktualisieren des Films', error)
      );
    } else {
      this.filmService.erstelleFilm(this.film).subscribe(
        () => this.router.navigate(['/admin/filme']),
        error => console.error('Fehler beim Erstellen des Films', error)
      );
    }
  }
}