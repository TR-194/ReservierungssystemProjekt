import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';
import { Sitzreihe } from 'src/app/shared/models/sitzreihe.model';
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
  sitzreihen: Sitzreihe[] = [];
  ausgewaehlteSitze: number[] = [];
  film: Film | null = null;
  kinosaal: Kinosaal | null = null;
  gesamtpreis = 0;

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
            this.ladeSitzreihen(data.kinosaalId);
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

  ladeSitzreihen(kinosaalId: number): void {
    this.kafkaService.sendRequest<Sitzreihe[]>('sitzreihe.getByKinosaal', kinosaalId)
      .subscribe(
        sitzreihen => this.sitzreihen = sitzreihen,
        error => console.error('Fehler beim Laden der Sitzreihen', error)
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
    this.berechneGesamtpreis();
  }

  getSitzplatzKategorie(sitzplatzId: number): number | undefined {
    const sitzplatz = this.sitzplaetze.find(sitz => sitz.id === sitzplatzId);
    if (!sitzplatz) return undefined;
  
    const sitzreihe = this.sitzreihen.find(reihe => reihe.id === sitzplatz.sitzreiheId);
    
    return sitzreihe?.kategorieId as unknown as number;
  }

  berechneGesamtpreis(): void {
    if (!this.auffuehrung) return;

    this.gesamtpreis = this.ausgewaehlteSitze.reduce((sum, sitzId) => {
      const kategorieId = this.getSitzplatzKategorie(sitzId);
      if (kategorieId) {
        switch (kategorieId) {
          case 1: return sum + this.auffuehrung!.preismodell.parkettPreis;
          case 2: return sum + this.auffuehrung!.preismodell.logePreis;
          case 3: return sum + this.auffuehrung!.preismodell.logeMitServicePreis;
        }
      }
      return sum;
    }, 0);
  }

  reservieren(): void {
    if (this.auffuehrung) {
      this.router.navigate(['/reservierung-form'], { 
        queryParams: { 
          sitzplaetze: this.ausgewaehlteSitze.join(','), 
          auffuehrungId: this.auffuehrung.id,
          preis: this.gesamtpreis
        } 
      });
    }
  }

  buchen(): void {
    if (this.auffuehrung) {
      this.router.navigate(['/buchung-form'], { 
        queryParams: { 
          sitzplaetze: this.ausgewaehlteSitze.join(','), 
          auffuehrungId: this.auffuehrung.id,
          preis: this.gesamtpreis
        } 
      });
    }
  }
}
