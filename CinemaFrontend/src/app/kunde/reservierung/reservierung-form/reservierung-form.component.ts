import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-reservierung-form',
  imports: [ReactiveFormsModule],
  templateUrl: './reservierung-form.component.html',
  styleUrls: ['./reservierung-form.component.css']
})
export class ReservierungFormComponent {
  reservierungForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.reservierungForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      sitzplaetze: [[], Validators.required]
    });
  }

  onSubmit() {
    if (this.reservierungForm.valid) {
      console.log('Reservierung:', this.reservierungForm.value);
      // Später hier die Backend-Anbindung für die Reservierung
    }
  }
}