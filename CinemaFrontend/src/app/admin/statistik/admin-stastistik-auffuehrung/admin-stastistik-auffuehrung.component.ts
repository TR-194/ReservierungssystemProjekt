import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { KafkaService } from 'src/app/shared/services/kafka.service';

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

  constructor(private kafkaService: KafkaService) {}

  ngOnInit(): void {
    this.ladeAuffuehrungsStatistik();
  }

  ladeAuffuehrungsStatistik(): void {
    this.kafkaService.sendRequest<AuffuehrungStatistik[]>('statistikGetEinnahmenProAuffuehrung')
      .subscribe(
        data => this.auffuehrungenStatistik = data,
        error => console.error('Fehler beim Laden der Aufführungsstatistik:', error)
      );
  }
}