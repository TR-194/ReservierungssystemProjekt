import { Component } from '@angular/core';
import {  ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KinosaalService } from 'src/app/shared/services/kinosaal.service';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Router } from '@angular/router';
import { Sitzreihe } from 'src/app/shared/models/sitzreihe.model';
import { KategorieTyp } from 'src/app/shared/models/sitzkategorie.model';

@Component({
  selector: 'app-admin-kinosaal-form',
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
  kategorieTypen = Object.values(KategorieTyp);
  ausgewaehlteKategorie: KategorieTyp = KategorieTyp.PARKETT;
  anzahlSitzplaetze = 0;

  constructor(private kinosaalService: KinosaalService, private router: Router) {}

  speichereKinosaal(): void {
    const neuerSaal: Kinosaal = { 
      id: 0, 
      name: this.name, 
      freigegeben: this.freigegeben, 
      sitzreihen: this.sitzreihen 
    };

    this.kinosaalService.erstelleKinosaal(neuerSaal).subscribe(
      () => this.router.navigate(['/admin/kinosaal']),
      error => console.error('Fehler beim Speichern des Kinosaals', error)
    );
  }

  hinzufuegenSitzreihe(): void {
    const neueSitzplaetze = Array.from({ length: this.anzahlSitzplaetze }, (_, i) => ({
      id: 0,
      nummer: i + 1,
      status: 'frei' as const
    }));

    const neueReihe: Sitzreihe = {
      id: 0, // Die ID wird vom Backend gesetzt
      reihenNummer: this.sitzreihen.length + 1,
      sitzplaetze: neueSitzplaetze,
      kategorie: { id: 0, name: this.ausgewaehlteKategorie, preis: 0 } // Ausgewählte Kategorie
    };
    this.sitzreihen.push(neueReihe);
  }

  entferneSitzreihe(index: number): void {
    this.sitzreihen.splice(index, 1);
    this.sitzreihen.forEach((reihe, i) => reihe.reihenNummer = i + 1); // Reihenummern neu ordnen
  }

  public navigateToKinosaal() {
    this.router.navigate(['/admin/kinosaal']);
  }
}
