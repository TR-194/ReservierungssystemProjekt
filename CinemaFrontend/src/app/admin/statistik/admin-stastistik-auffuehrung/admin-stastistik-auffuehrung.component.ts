import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

interface AuffuehrungStatistik {
  id: number;
  name: string;
  einnahmen: number;
}


@Component({
  selector: 'app-admin-stastistik-auffuehrung',
  imports: [CommonModule],
  templateUrl: './admin-stastistik-auffuehrung.component.html',
  styleUrl: './admin-stastistik-auffuehrung.component.css'
})
export class AdminStatistikAuffuehrungComponent {
  
  auffuehrungenStatistik: AuffuehrungStatistik[] = [
    { id: 1, name: 'Aufführung 1', einnahmen: 5000 },
    { id: 2, name: 'Aufführung 2', einnahmen: 3500 }
  ];
}