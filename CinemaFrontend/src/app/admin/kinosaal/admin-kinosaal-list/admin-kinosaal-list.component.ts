import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { KinosaalService } from 'src/app/shared/services/kinosaal.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-kinosaal-list',
  imports: [CommonModule],
  templateUrl: './admin-kinosaal-list.component.html',
  styleUrls: ['./admin-kinosaal-list.component.css']
})
export class AdminKinosaalListComponent {
  kinosäle: Kinosaal[] = [];

  constructor(private kinosaalService: KinosaalService, private router: Router) {}

  ngOnInit(): void {
    this.ladeKinosäle();
  }

  ladeKinosäle(): void {
    this.kinosaalService.getKinosäle().subscribe(
      (data: Kinosaal[]) => this.kinosäle = data,
      error => console.error('Fehler beim Laden der Kinosäle', error)
    );
  }

  erstelleNeuenKinosaal(): void {
    this.router.navigate(['/admin/kinosaal/neuerSaal']);
  }
}
