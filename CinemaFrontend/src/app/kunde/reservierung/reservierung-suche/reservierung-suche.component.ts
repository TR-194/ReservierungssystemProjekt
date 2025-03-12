import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Reservierung } from 'src/app/shared/models/reservierung.model';
import { KafkaService } from 'src/app/shared/services/kafka.service';

@Component({
  selector: 'app-reservierung-suche',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './reservierung-suche.component.html',
  styleUrls: ['./reservierung-suche.component.css']
})
export class ReservierungSucheComponent {
  suchForm: FormGroup;
  reservierungGefunden = false;
  reservierungsDaten: Reservierung | null = null;
  isLoading = false;

  constructor(private fb: FormBuilder, private kafkaService: KafkaService) {
    this.suchForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSearch() {
    if (this.suchForm.valid) {
      const email = this.suchForm.value.email;
      this.isLoading = true;
      console.log('Suche Reservierung für:', email);
      
      this.kafkaService.sendRequest<Reservierung>('reservierung.getByEmail', { email })
        .subscribe(
          (reservierung: Reservierung) => {
            this.isLoading = false;
            if (reservierung) {
              this.reservierungGefunden = true;
              this.reservierungsDaten = reservierung;
            } else {
              this.reservierungGefunden = false;
              this.reservierungsDaten = null;
            }
          },
          (error) => {
            this.isLoading = false;
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
      this.kafkaService.sendRequest<Reservierung>('reservierung.convertToBuchung', { reservierungsId: this.reservierungsDaten.id })
        .subscribe(
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
      this.kafkaService.sendRequest<void>('reservierung.cancel', { reservierungsId: this.reservierungsDaten.id })
        .subscribe(
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
