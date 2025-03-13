import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Film } from 'src/app/shared/models/film.model';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { Preismodell } from 'src/app/shared/models/preismodell.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-auffuehrung-form',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './admin-auffuehrung-form.component.html',
  styleUrls: ['./admin-auffuehrung-form.component.css']
})
export class AdminAuffuehrungFormComponent implements OnInit {
  auffuehrung = {
    id: 0,
    filmId: null as number | null,
    datum: '',
    uhrzeit: '',
    kinosaalId: null as number | null,
    preismodell: {
      logeMitServicePreis: 0,
      logePreis: 0,
      parkettPreis: 0
    } as Preismodell
  };

  filme: Film[] = [];
  kinosaele: Kinosaal[] = [];

  constructor(
    private kafkaService: KafkaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadFilme();
    this.loadKinosaele();
  }

  loadFilme() {
    this.kafkaService.sendRequest<Film[]>('filmGetAll').subscribe(data => {
      this.filme = data;
    });
  }

  loadKinosaele() {
    this.kafkaService.sendRequest<Kinosaal[]>('kinosaalGetAll').subscribe(data => {
      this.kinosaele = data;
    });
  }

  submitForm() {
    if (this.auffuehrung.filmId && this.auffuehrung.kinosaalId) {
      this.kafkaService.sendRequest<void>('auffuehrungCreate', this.auffuehrung).subscribe(() => {
        this.router.navigate(['/admin/auffuehrungen']);
      });
    } else {
      console.error('Film oder Kinosaal wurde nicht ausgewählt.');
    }
  }
}
