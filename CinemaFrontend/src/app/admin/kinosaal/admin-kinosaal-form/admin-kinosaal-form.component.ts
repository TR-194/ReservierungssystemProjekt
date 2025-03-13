import { Component } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Router } from '@angular/router';
import { Sitzreihe } from 'src/app/shared/models/sitzreihe.model';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';
import { Sitzkategorie } from 'src/app/shared/models/sitzkategorie.model';

@Component({
  selector: 'app-admin-kinosaal-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './admin-kinosaal-form.component.html',
  styleUrls: ['./admin-kinosaal-form.component.css']
})
export class AdminKinosaalFormComponent {
  name = '';
  freigegeben = false;
  sitzreihen: Sitzreihe[] = [];
  kategorieTypen: Sitzkategorie[] = [
    { id: 1, name: 'PARKETT', preis: 8.00 },
    { id: 2, name: 'LOGE', preis: 10.00 },
    { id: 3, name: 'LOGE_MIT_SERVICE', preis: 15.00 }
  ];
  kinosaalId = 0;
  anzahlSitzplaetze = 0;
  ausgewaehlteKategorie = 1; // Standardmäßig PARKETT

  constructor(private kafkaService: KafkaService, private router: Router) {}

  speichereKinosaal(): void {
    const neuerSaal: Kinosaal = { 
      id: this.kinosaalId, 
      name: this.name, 
      freigegeben: this.freigegeben, 
      sitzreihen: this.sitzreihen.map(reihe => ({
        id: 0,  // ID wird vom Backend generiert
        reihenNummer: reihe.reihenNummer,
        kategorieId: reihe.kategorieId,
        sitzplaetze: reihe.sitzplaetze.map(sitz => ({
          id: 0,  // ID wird vom Backend generiert
          nummer: sitz.nummer,
          status: sitz.status
        }))
      }))
    };

    const nachricht = {
      eventType: "kinosaalCreate",
      data: neuerSaal
    };

    this.kafkaService.sendRequest('kinosaalCreate', nachricht).subscribe(
      () => this.router.navigate(['/admin/kinosaal']),
      error => console.error('Fehler beim Speichern des Kinosaals', error)
    );
  }

  hinzufuegenSitzreihe(): void {
    if (this.anzahlSitzplaetze <= 0) {
      console.error("Die Anzahl der Sitzplätze muss größer als 0 sein.");
      return;
    }

    const neueReihe: Sitzreihe = {
      id: 0, // ID wird vom Backend gesetzt
      reihenNummer: this.sitzreihen.length + 1,
      kategorieId: this.ausgewaehlteKategorie,
      sitzplaetze: Array.from({ length: this.anzahlSitzplaetze }, (_, i) => ({
        id: 0,
        nummer: i + 1,
        status: 'frei'
      }))
    };

    this.sitzreihen.push(neueReihe);
  }

  entferneSitzreihe(index: number): void {
    this.sitzreihen.splice(index, 1);
    this.sitzreihen.forEach((reihe, i) => reihe.reihenNummer = i + 1);
  }

  getKategorieName(kategorieId: number): string {
    const kategorie = this.kategorieTypen.find(k => k.id === kategorieId);
    return kategorie ? kategorie.name : 'Unbekannt';
  }

  navigateToKinosaal() {
    this.router.navigate(['/admin/kinosaal']);
  }
}
