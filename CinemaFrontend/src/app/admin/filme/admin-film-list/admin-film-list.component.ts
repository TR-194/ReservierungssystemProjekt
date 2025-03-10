import { Component, OnInit } from '@angular/core';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Film } from 'src/app/shared/models/film.model';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-film-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-film-list.component.html',
  styleUrls: ['./admin-film-list.component.css']
})
export class AdminFilmListComponent implements OnInit {
  filme: Film[] = [];

  constructor(private kafkaService: KafkaService, private router: Router) {}

  ngOnInit(): void {
    this.ladeFilme();
  }

  ladeFilme(): void {
    this.kafkaService.sendRequest<Film[]>('film.getAll').subscribe(
      (data: Film[]) => this.filme = data,
      error => console.error('Fehler beim Laden der Filme', error)
    );
  }

  erstelleNeuenFilm(): void {
    this.router.navigate(['/admin/filme/neuerFilm']);
  }

  bearbeiteFilm(id: number): void {
    this.router.navigate([`/admin/filme/bearbeiten/${id}`]);
  }

  loescheFilm(id: number): void {
    this.kafkaService.sendRequest<void>('film.delete', id).subscribe(
      () => this.ladeFilme(),
      error => console.error('Fehler beim Löschen des Films', error)
    );
  }
}
