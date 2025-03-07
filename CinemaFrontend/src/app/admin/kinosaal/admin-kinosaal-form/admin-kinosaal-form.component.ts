import { Component } from '@angular/core';
import {  ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KinosaalService } from 'src/app/shared/services/kinosaal.service';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Router } from '@angular/router';

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

  constructor(private kinosaalService: KinosaalService, private router: Router) {}

  speichereKinosaal(): void {
    const neuerSaal: Kinosaal = { name: this.name, freigegeben: this.freigegeben };

    this.kinosaalService.erstelleKinosaal(neuerSaal).subscribe(
      () => this.router.navigate(['/admin/kinosaal']),
      error => console.error('Fehler beim Speichern des Kinosals', error)
    );
  }
}