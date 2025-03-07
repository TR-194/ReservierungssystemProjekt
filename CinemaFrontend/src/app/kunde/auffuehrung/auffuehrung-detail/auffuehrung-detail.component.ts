import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuffuehrungService } from 'src/app/shared/services/auffuehrung.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';

@Component({
  selector: 'app-auffuehrung-detail',
  templateUrl: './auffuehrung-detail.component.html',
  styleUrls: ['./auffuehrung-detail.component.css']
})
export class AuffuehrungDetailComponent implements OnInit {
  auffuehrung: Auffuehrung | null = null;

  constructor(
    private route: ActivatedRoute,
    private auffuehrungService: AuffuehrungService
  ) {}

  ngOnInit(): void {
    this.ladeAuffuehrung();
  }

  ladeAuffuehrung(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.auffuehrungService.getAuffuehrungById(id).subscribe(
        (data: Auffuehrung) => this.auffuehrung = data,
        error => console.error('Fehler beim Laden der Aufführung', error)
      );
    }
  }
}