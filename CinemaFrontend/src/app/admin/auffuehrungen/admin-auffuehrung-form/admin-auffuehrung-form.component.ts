import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuffuehrungService } from 'src/app/shared/services/auffuehrung.service';
import { FilmService } from 'src/app/shared/services/film.service';
import { KinosaalService } from 'src/app/shared/services/kinosaal.service';
import { Film } from 'src/app/shared/models/film.model';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';

@Component({
  selector: 'app-admin-auffuehrung-form',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './admin-auffuehrung-form.component.html',
  styleUrls: ['./admin-auffuehrung-form.component.css']
})
export class AdminAuffuehrungFormComponent implements OnInit {
  auffuehrung = {
    id: 0,
    film: {} as Film,
    datum: '',
    uhrzeit: '',
    saal: '',
    kinosaal: {} as Kinosaal
  };

  filme: Film[] = [];
  kinosaele: Kinosaal[] = [];

  constructor(
    private auffuehrungService: AuffuehrungService,
    private filmService: FilmService,
    private kinosaalService: KinosaalService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadFilme();
    this.loadKinosaele();
  }

  loadFilme() {
    this.filmService.getFilme().subscribe(data => {
      this.filme = data;
    });
  }

  loadKinosaele() {
    this.kinosaalService.getKinosaele().subscribe(data => {
      this.kinosaele = data;
    });
  }

  submitForm() {
    this.auffuehrungService.addAuffuehrung(this.auffuehrung).subscribe(() => {
      this.router.navigate(['/admin/auffuehrungen']);
    });
  }
}
