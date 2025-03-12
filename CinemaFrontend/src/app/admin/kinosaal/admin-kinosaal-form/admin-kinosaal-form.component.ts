import { Component } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Router } from '@angular/router';
import { Sitzreihe } from 'src/app/shared/models/sitzreihe.model';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';
import { KategorieTyp } from 'src/app/shared/models/sitzkategorie.model';

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
  sitzreihenIds: number[] = [];
  kategorieTypen = Object.values(KategorieTyp);
  kinosaalId = 0;
  anzahlSitzplaetze = 0;
  ausgewaehlteKategorie: KategorieTyp = KategorieTyp.PARKETT;

  constructor(private kafkaService: KafkaService, private router: Router) {}

  speichereKinosaal(): void {
    const neuerSaal: Kinosaal = { 
      id: this.kinosaalId, 
      name: this.name, 
      freigegeben: this.freigegeben, 
      sitzreihenIds: [...this.sitzreihenIds] 
    };

    this.kafkaService.sendRequest<Kinosaal>('kinosaal.create', neuerSaal).subscribe(
      (gespeicherterSaal) => {
        this.kinosaalId = gespeicherterSaal.id;
        this.speichereSitzreihen();
      },
      error => console.error('Fehler beim Speichern des Kinosaals', error)
    );
  }

  speichereSitzreihen(): void {
    this.sitzreihen.forEach((reihe) => {
      reihe.kinosaalId = this.kinosaalId;

      this.kafkaService.sendRequest<Sitzreihe>('sitzreihe.create', reihe).subscribe(
        (gespeicherteReihe) => {
          reihe.id = gespeicherteReihe.id;
          reihe.sitzplatzIds = gespeicherteReihe.sitzplatzIds;
        },
        error => console.error('Fehler beim Speichern der Sitzreihe', error)
      );
    });

    this.router.navigate(['/admin/kinosaal']);
  }

  hinzufuegenSitzreihe(): void {
    if (this.anzahlSitzplaetze <= 0) {
      console.error("Die Anzahl der Sitzplätze muss größer als 0 sein.");
      return;
    }

    const neueSitzreiheId = this.sitzreihen.length + 1;
    this.sitzreihenIds.push(neueSitzreiheId);

    const neueSitzplaetze: Sitzplatz[] = Array.from({ length: this.anzahlSitzplaetze }, (_, i) => ({
      id: 0, 
      nummer: i + 1,
      status: 'frei' as const,
      sitzreiheId: neueSitzreiheId 
    }));

    const neueReihe: Sitzreihe = {
      id: neueSitzreiheId,
      reihenNummer: this.sitzreihen.length + 1,
      kategorieId: this.ausgewaehlteKategorie,
      sitzplatzIds: neueSitzplaetze.map(sp => sp.id),
      kinosaalId: this.kinosaalId
    };

    this.sitzreihen.push(neueReihe);
  }

  entferneSitzreihe(index: number): void {
    this.sitzreihenIds.splice(index, 1);
    this.sitzreihen.splice(index, 1);
    this.sitzreihen.forEach((reihe, i) => reihe.reihenNummer = i + 1);
  }

  getSitzplaetzeAnzahl(reihe: Sitzreihe): number[] {
    return Array.from({ length: reihe.sitzplatzIds.length || this.anzahlSitzplaetze });
  }

  navigateToKinosaal() {
    this.router.navigate(['/admin/kinosaal']);
  }
}
