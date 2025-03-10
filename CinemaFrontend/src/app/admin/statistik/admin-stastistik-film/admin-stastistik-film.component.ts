import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { KafkaService } from 'src/app/shared/services/kafka.service';

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

  constructor(private kafkaService: KafkaService) {}

  ngOnInit(): void {
    this.ladeFilmStatistik();
  }

  ladeFilmStatistik(): void {
    this.kafkaService.sendRequest<FilmStatistik[]>('statistik.getEinnahmenProFilm')
      .subscribe(
        data => this.filmeStatistik = data,
        error => console.error('Fehler beim Laden der Filmstatistik:', error)
      );
  }
}