import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FilmStatistikService } from 'src/app/shared/services/film-statistik.service';

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
export class AdminStatistikFilmComponent implements OnInit {
  filmeStatistik: FilmStatistik[] = [];

  constructor(private filmStatistikService: FilmStatistikService) {}

  ngOnInit(): void {
    this.filmStatistikService.getFilmStatistik().subscribe(data => {
      this.filmeStatistik = data;
    });
  }
}