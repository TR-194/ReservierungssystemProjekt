import { Component, OnInit } from '@angular/core';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Router } from '@angular/router';
import { Film } from 'src/app/shared/models/film.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auffuehrung-list',
  imports: [CommonModule],
  templateUrl: './auffuehrung-list.component.html',
  styleUrls: ['./auffuehrung-list.component.css']
})
export class AuffuehrungListComponent implements OnInit {
  auffuehrungen: Auffuehrung[] = [];
  filme: Map<number, string> = new Map(); // Speichert die Filmnamen zu den Film-IDs

  constructor(
    private kafkaService: KafkaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ladeAuffuehrungen();
  }

  ladeAuffuehrungen(): void {
    this.kafkaService.sendRequest<Auffuehrung[]>('auffuehrungGetAll')
      .subscribe(
        (data: Auffuehrung[]) => {
          this.auffuehrungen = data;
          this.ladeFilme(data.map(a => a.filmId)); // Lade die Filmdetails
        },
        error => console.error('Fehler beim Laden der Aufführungen', error)
      );
  }

  ladeFilme(filmIds: number[]): void {
    filmIds.forEach(filmId => {
      if (!this.filme.has(filmId)) { // Verhindert doppelte Anfragen
        this.kafkaService.sendRequest<Film>('filmGetById', filmId)
          .subscribe(
            film => this.filme.set(film.id, film.titel),
            error => console.error(`Fehler beim Laden des Films mit ID ${filmId}`, error)
          );
      }
    });
  }

  getFilmTitel(filmId: number): string {
    return this.filme.get(filmId) || 'Lädt...';
  }

  geheZuDetail(id: number): void {
    this.router.navigate(['/auffuehrung', id]);
  }
}
