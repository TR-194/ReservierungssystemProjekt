import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

interface FilmStatistik {
  id: number;
  titel: string;
  einnahmen: number;
}


@Component({
  selector: 'app-admin-stastistik-film',
  imports: [CommonModule],
  templateUrl: './admin-stastistik-film.component.html',
  styleUrl: './admin-stastistik-film.component.css'
})
export class AdminStatistikFilmComponent {
  filmeStatistik: FilmStatistik[] = [
    { id: 1, titel: 'Film A', einnahmen: 12000 },
    { id: 2, titel: 'Film B', einnahmen: 8000 }
  ];
}