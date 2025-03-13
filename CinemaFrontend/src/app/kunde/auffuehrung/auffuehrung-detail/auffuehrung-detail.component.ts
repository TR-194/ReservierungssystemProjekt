import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';
import { Sitzreihe } from 'src/app/shared/models/sitzreihe.model';
import { Film } from 'src/app/shared/models/film.model';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Sitzkategorie } from 'src/app/shared/models/sitzkategorie.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auffuehrung-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './auffuehrung-detail.component.html',
  styleUrls: ['./auffuehrung-detail.component.css']
})
export class AuffuehrungDetailComponent implements OnInit {
  auffuehrung: Auffuehrung | null = null;
  sitzreihen: Sitzreihe[] = [];
  ausgewaehlteSitze: Sitzplatz[] = [];
  film: Film | null = null;
  kinosaal: Kinosaal | null = null;
  gesamtpreis = 0;

  kategorieTypen: Sitzkategorie[] = [
    { id: 1, name: 'PARKETT', preis: 8.00 },
    { id: 2, name: 'LOGE', preis: 10.00 },
    { id: 3, name: 'LOGE_MIT_SERVICE', preis: 15.00 }
  ];

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
      this.kafkaService.sendRequest<Auffuehrung>('auffuehrungGetById', id)
        .subscribe(
          data => {
            this.auffuehrung = data;
            this.ladeKinosaal(data.kinosaalId);
            this.ladeFilm(data.filmId);
          },
          error => console.error('Fehler beim Laden der Aufführung', error)
        );
    }
  }

  ladeKinosaal(kinosaalId: number): void {
    this.kafkaService.sendRequest<Kinosaal>('kinosaalGetById', kinosaalId)
      .subscribe(
        data => {
          this.kinosaal = data;
          this.sitzreihen = data.sitzreihen;
        },
        error => console.error('Fehler beim Laden des Kinosaals', error)
      );
  }

  ladeFilm(filmId: number): void {
    this.kafkaService.sendRequest<Film>('filmGetById', filmId)
      .subscribe(
        data => this.film = data,
        error => console.error('Fehler beim Laden des Films', error)
      );
  }

  getKategorieName(kategorieId: number): string {
    const kategorie = this.kategorieTypen.find(k => k.id === kategorieId);
    return kategorie ? kategorie.name : 'Unbekannt';
  }

  onSitzplatzAuswaehlen(sitzplatz: Sitzplatz): void {
    const index = this.ausgewaehlteSitze.findIndex(s => s.id === sitzplatz.id);
    if (index === -1) {
      this.ausgewaehlteSitze.push(sitzplatz);
    } else {
      this.ausgewaehlteSitze.splice(index, 1);
    }
    this.berechneGesamtpreis();
  }

  berechneGesamtpreis(): void {
    if (!this.auffuehrung) return;

    this.gesamtpreis = this.ausgewaehlteSitze.reduce((sum, sitz) => {
      const sitzreihe = this.sitzreihen.find(reihe => reihe.sitzplaetze.includes(sitz));
      if (sitzreihe) {
        switch (sitzreihe.kategorieId) {
          case 1: return sum + this.auffuehrung!.preismodell.parkettPreis;
          case 2: return sum + this.auffuehrung!.preismodell.logePreis;
          case 3: return sum + this.auffuehrung!.preismodell.logeMitServicePreis;
        }
      }
      return sum;
    }, 0);
  }

  reservieren(): void {
    this.router.navigate(['/reservierung-form'], { 
      queryParams: { 
        sitzplaetze: this.ausgewaehlteSitze.map(s => s.id).join(','), 
        auffuehrungId: this.auffuehrung?.id, 
        preis: this.gesamtpreis 
      } 
    });
  }

  buchen(): void {
    this.router.navigate(['/buchung-form'], { 
      queryParams: { 
        sitzplaetze: this.ausgewaehlteSitze.map(s => s.id).join(','), 
        auffuehrungId: this.auffuehrung?.id, 
        preis: this.gesamtpreis 
      } 
    });
  }
}
