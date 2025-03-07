import { Component, OnInit } from '@angular/core';
import { AuffuehrungService } from 'src/app/shared/services/auffuehrung.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auffuehrung-list',
  templateUrl: './auffuehrung-list.component.html',
  styleUrls: ['./auffuehrung-list.component.css']
})
export class AuffuehrungListComponent implements OnInit {
  auffuehrungen: Auffuehrung[] = [];

  constructor(
    private auffuehrungService: AuffuehrungService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ladeAuffuehrungen();
  }

  ladeAuffuehrungen(): void {
    this.auffuehrungService.getAuffuehrungen().subscribe(
      (data: Auffuehrung[]) => this.auffuehrungen = data,
      error => console.error('Fehler beim Laden der Aufführungen', error)
    );
  }

  geheZuDetail(id: number): void {
    this.router.navigate(['/auffuehrung', id]);
  }
}
