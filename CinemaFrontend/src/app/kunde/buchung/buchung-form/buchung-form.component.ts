import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-buchung-form',
  imports: [ReactiveFormsModule],
  templateUrl: './buchung-form.component.html',
  styleUrls: ['./buchung-form.component.css']
})
export class BuchungFormComponent {
  buchungForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.buchungForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      sitzplaetze: [[], Validators.required]
    });
  }

  onSubmit() {
    if (this.buchungForm.valid) {
      console.log('Buchung:', this.buchungForm.value);
      // Später hier die Backend-Anbindung für die Buchung
    }
  }
}
