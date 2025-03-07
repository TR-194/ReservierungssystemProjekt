import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-auffuehrung-form',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './admin-auffuehrung-form.component.html',
  styleUrls: ['./admin-auffuehrung-form.component.css']
})
export class AdminAuffuehrungFormComponent {
  auffuehrung = {
    film: '',
    datum: '',
    uhrzeit: '',
    saal: ''
  };

  submitForm() {
    console.log('Neue Aufführung:', this.auffuehrung);
    // Hier später API-Anbindung für Speicherung
  }
}
