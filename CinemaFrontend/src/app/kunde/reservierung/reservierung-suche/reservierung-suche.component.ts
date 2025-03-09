import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-reservierung-suche',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './reservierung-suche.component.html',
  styleUrls: ['./reservierung-suche.component.css']
})
export class ReservierungSucheComponent {
  suchForm: FormGroup;
  reservierungGefunden = false;
  reservierungsDaten: { name: string; email: string; sitzplaetze: string[]; status: string } | null = null;

  constructor(private fb: FormBuilder) {
    this.suchForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSearch() {
    if (this.suchForm.valid) {
      const email = this.suchForm.value.email;
      console.log('Suche Reservierung für:', email);
      
      // Später hier die Backend-Anbindung, um die Reservierung zu suchen
      // Temporäre Simulation einer gefundenen Reservierung
      setTimeout(() => {
        this.reservierungGefunden = true;
        this.reservierungsDaten = {
          name: 'Max Mustermann',
          email: email,
          sitzplaetze: ['A1', 'A2'],
          status: 'Reserviert'
        };
      }, 1000);
    }
  }

  onBuchen() {
    console.log('Reservierung in Buchung umwandeln:', this.reservierungsDaten);
    // Später Backend-Aufruf zum Buchen der Reservierung
  }

  onStornieren() {
    console.log('Reservierung stornieren:', this.reservierungsDaten);
    // Später Backend-Aufruf zur Stornierung der Reservierung
    this.reservierungGefunden = false;
    this.reservierungsDaten = null;
  }
}
