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
  sitzreihenIds: number[] = []; // IDs statt vollständige Objekte
  kategorieTypen = Object.values(KategorieTyp);
  ausgewaehlteKategorie: KategorieTyp = KategorieTyp.PARKETT;
  anzahlSitzplaetze = 0;
  kinosaalId = 0; // Platzhalter für die ID des gespeicherten Kinosaals

  constructor(private kafkaService: KafkaService, private router: Router) {}

  speichereKinosaal(): void {
    const neuerSaal: Kinosaal = { 
      id: this.kinosaalId, 
      name: this.name, 
      freigegeben: this.freigegeben, 
      sitzreihenIds: [...this.sitzreihenIds] // IDs der Sitzreihen senden
    };

    this.kafkaService.sendRequest<Kinosaal>('kinosaal.create', neuerSaal).subscribe(
      (gespeicherterSaal) => {
        this.kinosaalId = gespeicherterSaal.id; // Backend setzt ID
        this.speichereSitzreihen();
      },
      error => console.error('Fehler beim Speichern des Kinosaals', error)
    );
  }

  speichereSitzreihen(): void {
    this.sitzreihen.forEach((reihe) => {
      reihe.kinosaalId = this.kinosaalId; // Kinosaal-ID setzen

      this.kafkaService.sendRequest<Sitzreihe>('sitzreihe.create', reihe).subscribe(
        (gespeicherteReihe) => {
          reihe.id = gespeicherteReihe.id; // ID aus Backend übernehmen
          reihe.sitzplatzIds = gespeicherteReihe.sitzplatzIds;
        },
        error => console.error('Fehler beim Speichern der Sitzreihe', error)
      );
    });

    this.router.navigate(['/admin/kinosaal']);
  }

  hinzufuegenSitzreihe(): void {
    const neueSitzreiheId = this.sitzreihen.length + 1; // Simulierte ID
    this.sitzreihenIds.push(neueSitzreiheId); // ID speichern

    const neueSitzplaetze: Sitzplatz[] = Array.from({ length: this.anzahlSitzplaetze }, (_, i) => ({
      id: 0, // Backend setzt ID
      nummer: i + 1,
      status: 'frei' as const,
      sitzreiheId: neueSitzreiheId // Sitzreihe ID zuweisen
    }));

    const neueReihe: Sitzreihe = {
      id: neueSitzreiheId, // Simulierte ID
      reihenNummer: this.sitzreihen.length + 1,
      sitzplatzIds: neueSitzplaetze.map(sp => sp.id), // Sitzplatz-IDs speichern
      kategorieId: 0, // Platzhalter für Kategorie ID
      kinosaalId: this.kinosaalId // Kinosaal-ID setzen
    };

    this.sitzreihen.push(neueReihe);
  }

  entferneSitzreihe(index: number): void {
    this.sitzreihenIds.splice(index, 1);
    this.sitzreihen.splice(index, 1);
    this.sitzreihen.forEach((reihe, i) => reihe.reihenNummer = i + 1);
  }

  navigateToKinosaal() {
    this.router.navigate(['/admin/kinosaal']);
  }
}
