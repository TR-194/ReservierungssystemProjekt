import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuffuehrungService } from 'src/app/shared/services/auffuehrung.service';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';

@Component({
  selector: 'app-admin-auffuehrung-list',
  imports: [CommonModule],
  templateUrl: './admin-auffuehrung-list.component.html',
  styleUrls: ['./admin-auffuehrung-list.component.css']
})
export class AdminAuffuehrungListComponent implements OnInit {
  auffuehrungen: Auffuehrung[] = [];

  constructor(private auffuehrungService: AuffuehrungService, private router: Router) {}

  ngOnInit() {
    this.loadAuffuehrungen();
  }

  loadAuffuehrungen() {
    this.auffuehrungService.getAuffuehrungen().subscribe(data => {
      this.auffuehrungen = data;
    });
  }

  deleteAuffuehrung(id: number) {
    this.auffuehrungService.deleteAuffuehrung(id).subscribe(() => {
      this.loadAuffuehrungen();
    });
  }

  navigateToNewAuffuehrung() {
    this.router.navigate(['admin/auffuehrung/neueAuffuehrung']);
  }
}
