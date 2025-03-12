import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';
import { Film } from 'src/app/shared/models/film.model';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auffuehrung-detail',
  imports: [CommonModule],
  templateUrl: './auffuehrung-detail.component.html',
  styleUrls: ['./auffuehrung-detail.component.css']
})
export class AuffuehrungDetailComponent implements OnInit {
  auffuehrung: Auffuehrung | null = null;
  sitzplaetze: Sitzplatz[] = [];
  ausgewaehlteSitze: number[] = [];
  film: Film | null = null;
  kinosaal: Kinosaal | null = null;

  constructor(
    private route: ActivatedRoute,
    private kafkaService: KafkaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ladeAuffuehrung();
  }

  ladeAuffuehrung(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.kafkaService.sendRequest<Auffuehrung>('auffuehrung.getById', id)
        .subscribe(
          data => {
            this.auffuehrung = data;
            this.ladeSitzplaetze(id);
            this.ladeFilm(data.filmId);
            this.ladeKinosaal(data.kinosaalId);
          },
          error => console.error('Fehler beim Laden der Aufführung', error)
        );
    }
  }

  ladeSitzplaetze(auffuehrungId: number): void {
    this.kafkaService.sendRequest<Sitzplatz[]>('sitzplatz.getByAuffuehrung', auffuehrungId)
      .subscribe(
        sitzplaetze => this.sitzplaetze = sitzplaetze,
        error => console.error('Fehler beim Laden der Sitzplätze', error)
      );
  }

  ladeFilm(filmId: number): void {
    this.kafkaService.sendRequest<Film>('film.getById', filmId)
      .subscribe(
        data => this.film = data,
        error => console.error('Fehler beim Laden des Films', error)
      );
  }

  ladeKinosaal(kinosaalId: number): void {
    this.kafkaService.sendRequest<Kinosaal>('kinosaal.getById', kinosaalId)
      .subscribe(
        data => this.kinosaal = data,
        error => console.error('Fehler beim Laden des Kinosaals', error)
      );
  }

  onSitzplatzAuswaehlen(sitzplatzId: number): void {
    const index = this.ausgewaehlteSitze.indexOf(sitzplatzId);
    if (index === -1) {
      this.ausgewaehlteSitze.push(sitzplatzId);
    } else {
      this.ausgewaehlteSitze.splice(index, 1);
    }
  }

  reservieren(): void {
    if (this.auffuehrung) {
      this.router.navigate(['/reservierung-form'], { 
        queryParams: { 
          sitzplaetze: this.ausgewaehlteSitze.join(','), 
          auffuehrungId: this.auffuehrung.id 
        } 
      });
    }
  }

  buchen(): void {
    if (this.auffuehrung) {
      this.router.navigate(['/buchung-form'], { 
        queryParams: { 
          sitzplaetze: this.ausgewaehlteSitze.join(','), 
          auffuehrungId: this.auffuehrung.id 
        } 
      });
    }
  }
}
