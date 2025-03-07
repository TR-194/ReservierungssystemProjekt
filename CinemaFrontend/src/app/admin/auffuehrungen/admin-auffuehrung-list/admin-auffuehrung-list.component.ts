import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

interface Auffuehrung {
  id: number;
  film: string;
  datum: string;
  uhrzeit: string;
  saal: string;
}

@Component({
  selector: 'app-admin-auffuehrung-list',
  imports: [CommonModule],
  templateUrl: './admin-auffuehrung-list.component.html',
  styleUrls: ['./admin-auffuehrung-list.component.css']
})
export class AdminAuffuehrungListComponent {
  auffuehrungen: Auffuehrung[] = [
    { id: 1, film: 'Film A', datum: '2025-03-10', uhrzeit: '18:00', saal: 'Saal 1' },
    { id: 2, film: 'Film B', datum: '2025-03-11', uhrzeit: '20:00', saal: 'Saal 2' }
  ];

  deleteAuffuehrung(id: number) {
    this.auffuehrungen = this.auffuehrungen.filter(a => a.id !== id);
  }
}
