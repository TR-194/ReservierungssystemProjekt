import { Component, OnInit } from '@angular/core';
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
export class AdminKinosaalListComponent implements OnInit {
  kinosaele: Kinosaal[] = [];
  errorMessage: string | null = null;

  constructor(private kinosaalService: KinosaalService, private router: Router) {}

  ngOnInit(): void {
    this.ladeKinosäle();
  }

  ladeKinosäle(): void {
    this.kinosaalService.getKinosaele().subscribe({
      next: (data) => (this.kinosaele = data),
      error: (err) => (this.errorMessage = 'Fehler beim Laden der Kinosäle.')
    });
  }

  loescheKinosaal(id: number): void {
    if (confirm('Möchtest du diesen Kinosaal wirklich löschen?')) {
      this.kinosaalService.deleteKinosaal(id).subscribe({
        next: () => this.ladeKinosäle(),
        error: () => (this.errorMessage = 'Fehler beim Löschen des Kinosaals.')
      });
    }
  }

  toggleFreigabe(id: number): void {
    this.kinosaalService.freigeben(id).subscribe({
      next: () => this.ladeKinosäle(),
      error: () => (this.errorMessage = 'Fehler beim Ändern des Freigabestatus.')
    });
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}