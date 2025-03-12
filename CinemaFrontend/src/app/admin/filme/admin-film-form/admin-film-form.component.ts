import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Film } from 'src/app/shared/models/film.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-admin-film-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-film-form.component.html',
  styleUrls: ['./admin-film-form.component.css']
})
export class AdminFilmFormComponent implements OnInit {
  film: Film = { id: 0, titel: '', dauer: 0, fsk: 0 };
  filmId?: number;

  constructor(
    private kafkaService: KafkaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.filmId = this.route.snapshot.params['id'];
    if (this.filmId) {
      this.kafkaService.sendRequest<Film>('film.getById', this.filmId).subscribe(
        (data: Film) => this.film = data,
        error => console.error('Fehler beim Laden des Films', error)
      );
    }
  }

  speichereFilm(): void {
    if (this.filmId) {
      this.kafkaService.sendRequest<void>('film.update', this.film).subscribe(
        () => this.router.navigate(['/admin/filme']),
        error => console.error('Fehler beim Aktualisieren des Films', error)
      );
    } else {
      this.kafkaService.sendRequest<void>('film.create', this.film).subscribe(
        () => this.router.navigate(['/admin/filme']),
        error => console.error('Fehler beim Erstellen des Films', error)
      );
    }
  }
}
