import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuffuehrungService } from 'src/app/shared/services/auffuehrung.service';
import { ReservierungService } from 'src/app/shared/services/reservierung.service';
import { BuchungService } from 'src/app/shared/services/buchung.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auffuehrung-detail',
  imports: [CommonModule],
  templateUrl: './auffuehrung-detail.component.html',
  styleUrls: ['./auffuehrung-detail.component.css']
})
export class AuffuehrungDetailComponent implements OnInit {
  auffuehrung: Auffuehrung | null = null;
  ausgewaehlteSitze: number[] = [];
  reservierteSitze: number[] = [];
  gebuchteSitze: number[] = [];

  constructor(
    private route: ActivatedRoute,
    private auffuehrungService: AuffuehrungService,
    private reservierungService: ReservierungService,
    private buchungService: BuchungService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ladeAuffuehrung();
    this.ladeReservierteSitze();
    this.ladeGebuchteSitze();
  }

  ladeAuffuehrung(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.auffuehrungService.getAuffuehrungById(id).subscribe(
        (data: Auffuehrung) => this.auffuehrung = data,
        error => console.error('Fehler beim Laden der Aufführung', error)
      );
    }
  }

  ladeReservierteSitze(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.reservierungService.getReservierungen().subscribe(
        reservierungen => {
          this.reservierteSitze = reservierungen
            .filter(reservierung => reservierung.auffuehrung.id === id)
            .flatMap(reservierung => reservierung.sitzplaetze.map(Number));
        },
        error => console.error('Fehler beim Laden der reservierten Sitze', error)
      );
    }
  }

  ladeGebuchteSitze(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.buchungService.getBuchungen().subscribe(
        buchungen => {
          this.gebuchteSitze = buchungen
            .filter(buchung => buchung.auffuehrungId === id)
            .flatMap(buchung => buchung.sitzplaetze);
        },
        error => console.error('Fehler beim Laden der gebuchten Sitze', error)
      );
    }
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
          film: this.auffuehrung.film.id, 
          auffuehrung: this.auffuehrung.id, 
          kinosaal: this.auffuehrung.kinosaal.id 
        } 
      });
    }
  }

  buchen(): void {
    if (this.auffuehrung) {
      this.router.navigate(['/buchung-form'], { 
        queryParams: { 
          sitzplaetze: this.ausgewaehlteSitze.join(','), 
          film: this.auffuehrung.film.id, 
          auffuehrung: this.auffuehrung.id, 
          kinosaal: this.auffuehrung.kinosaal.id 
        } 
      });
    }
  }
}