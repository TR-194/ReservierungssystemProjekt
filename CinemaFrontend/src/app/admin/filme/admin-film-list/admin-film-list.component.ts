
import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/shared/services/film.service';
import { Film } from 'src/app/shared/models/film.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-film-list',
  templateUrl: './admin-film-list.component.html',
  styleUrls: ['./admin-film-list.component.css']
})
export class AdminFilmListComponent implements OnInit {
  filme: Film[] = [];

  constructor(private filmService: FilmService, private router: Router) {}

  ngOnInit(): void {
    this.ladeFilme();
  }

  ladeFilme(): void {
    this.filmService.getFilme().subscribe(
      (data: Film[]) => this.filme = data,
      error => console.error('Fehler beim Laden der Filme', error)
    );
  }

  erstelleNeuenFilm(): void {
    this.router.navigate(['/admin/filme/neu']);
  }

  bearbeiteFilm(id: number): void {
    this.router.navigate([`/admin/filme/bearbeiten/${id}`]);
  }

  loescheFilm(id: number): void {
    this.filmService.loescheFilm(id).subscribe(
      () => this.ladeFilme(),
      error => console.error('Fehler beim Löschen des Films', error)
    );
  }
}