import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';

@Component({
  selector: 'app-admin-auffuehrung-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-auffuehrung-list.component.html',
  styleUrls: ['./admin-auffuehrung-list.component.css']
})
export class AdminAuffuehrungListComponent implements OnInit {
  auffuehrungen: Auffuehrung[] = [];

  constructor(private kafkaService: KafkaService, private router: Router) {}

  ngOnInit() {
    this.loadAuffuehrungen();
  }

  loadAuffuehrungen() {
    this.kafkaService.sendRequest<Auffuehrung[]>('auffuehrung.getAll')
      .subscribe(
        (data: Auffuehrung[]) => {
          this.auffuehrungen = data;
        },
        error => console.error('Fehler beim Laden der Aufführungen:', error)
      );
  }

  deleteAuffuehrung(id: number) {
    this.kafkaService.sendRequest<void>('auffuehrung.delete', id)
      .subscribe(
        () => this.loadAuffuehrungen(),
        error => console.error('Fehler beim Löschen der Aufführung:', error)
      );
  }

  navigateToNewAuffuehrung() {
    this.router.navigate(['admin/auffuehrung/neueAuffuehrung']);
  }
}
