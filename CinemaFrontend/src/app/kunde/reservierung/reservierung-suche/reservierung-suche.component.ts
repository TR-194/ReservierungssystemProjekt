import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReservierungService } from 'src/app/shared/services/reservierung.service';
import { Reservierung } from 'src/app/shared/models/reservierung.model';

@Component({
  selector: 'app-reservierung-suche',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './reservierung-suche.component.html',
  styleUrls: ['./reservierung-suche.component.css']
})
export class ReservierungSucheComponent {
  suchForm: FormGroup;
  reservierungGefunden = false;
  reservierungsDaten: Reservierung | null = null;

  constructor(private fb: FormBuilder, private reservierungService: ReservierungService) {
    this.suchForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSearch() {
    if (this.suchForm.valid) {
      const email = this.suchForm.value.email;
      console.log('Suche Reservierung für:', email);
      
      this.reservierungService.getReservierungByEmail(email).subscribe(
        (reservierung: Reservierung) => {
          this.reservierungGefunden = true;
          this.reservierungsDaten = reservierung;
        },
        (error) => {
          console.error('Fehler beim Suchen der Reservierung:', error);
          this.reservierungGefunden = false;
          this.reservierungsDaten = null;
        }
      );
    }
  }

  onBuchen() {
    if (this.reservierungsDaten) {
      console.log('Reservierung in Buchung umwandeln:', this.reservierungsDaten);
      this.reservierungService.updateReservierung(this.reservierungsDaten.id, { ...this.reservierungsDaten, status: 'Gebucht' }).subscribe(
        (updatedReservierung: Reservierung) => {
          this.reservierungsDaten = updatedReservierung;
        },
        (error) => {
          console.error('Fehler beim Buchen der Reservierung:', error);
        }
      );
    }
  }

  onStornieren() {
    if (this.reservierungsDaten) {
      console.log('Reservierung stornieren:', this.reservierungsDaten);
      this.reservierungService.deleteReservierung(this.reservierungsDaten.id).subscribe(
        () => {
          this.reservierungGefunden = false;
          this.reservierungsDaten = null;
        },
        (error) => {
          console.error('Fehler beim Stornieren der Reservierung:', error);
        }
      );
    }
  }
}
