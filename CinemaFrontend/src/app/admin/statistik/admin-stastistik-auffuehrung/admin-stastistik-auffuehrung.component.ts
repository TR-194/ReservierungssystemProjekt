import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuffuehrungStatistikService } from 'src/app/shared/services/auffuehrung-statistik.service';

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
export class AdminStatistikAuffuehrungComponent implements OnInit {
  auffuehrungenStatistik: AuffuehrungStatistik[] = [];

  constructor(private auffuehrungStatistikService: AuffuehrungStatistikService) {}

  ngOnInit(): void {
    this.auffuehrungStatistikService.getAuffuehrungStatistik().subscribe(data => {
      this.auffuehrungenStatistik = data;
    });
  }
}