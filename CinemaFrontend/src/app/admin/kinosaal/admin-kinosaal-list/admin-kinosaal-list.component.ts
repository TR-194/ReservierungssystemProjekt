import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-kinosaal-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-kinosaal-list.component.html',
  styleUrls: ['./admin-kinosaal-list.component.css']
})
export class AdminKinosaalListComponent implements OnInit {
  kinosaele: Kinosaal[] = [];
  errorMessage: string | null = null;

  constructor(private kafkaService: KafkaService, private router: Router) {}

  ngOnInit(): void {
    this.ladeKinosaele();
  }

  ladeKinosaele(): void {
    this.kafkaService.sendRequest<Kinosaal[]>('kinosaalGetAll')
      .subscribe({
        next: (data) => this.kinosaele = data,
        error: () => this.errorMessage = 'Fehler beim Laden der Kinosäle.'
      });
  }

  loescheKinosaal(id: number): void {
    if (confirm('Möchtest du diesen Kinosaal wirklich löschen?')) {
      this.kafkaService.sendRequest<void>('kinosaalDelete', id).subscribe({
        next: () => this.ladeKinosaele(),
        error: () => this.errorMessage = 'Fehler beim Löschen des Kinosaals.'
      });
    }
  }

  toggleFreigabe(id: number): void {
    this.kafkaService.sendRequest<void>('kinosaalToggleFreigabe', id).subscribe({
      next: () => this.ladeKinosaele(),
      error: () => this.errorMessage = 'Fehler beim Ändern des Freigabestatus.'
    });
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
