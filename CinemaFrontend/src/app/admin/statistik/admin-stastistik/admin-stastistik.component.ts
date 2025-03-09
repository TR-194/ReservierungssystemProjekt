import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-stastistik',
  imports: [],
  templateUrl: './admin-stastistik.component.html',
  styleUrl: './admin-stastistik.component.css'
})
export class AdminStastistikComponent {
  constructor(private router: Router) {}

  navigateToAuffuehrungen() {
    this.router.navigate(['/admin/statistik/auffuehrungen']);
  }

  navigateToFilme() {
    this.router.navigate(['/admin/statistik/filme']);
  }
}
