import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Sitzplatz {
  id: number;
  reihe: number;
  nummer: number;
  status: 'frei' | 'reserviert' | 'gebucht';
}

@Component({
  selector: 'app-sitzplatz',
  imports: [CommonModule],
  templateUrl: './sitzplatz.component.html',
  styleUrls: ['./sitzplatz.component.css']
})
export class SitzplatzComponent {
  @Input() sitzplaetze: Sitzplatz[] = [];
  @Output() sitzplatzAusgewaehlt = new EventEmitter<Sitzplatz>();

  waehleSitzplatz(sitzplatz: Sitzplatz) {
    if (sitzplatz.status === 'frei') {
      this.sitzplatzAusgewaehlt.emit(sitzplatz);
    }
  }
}
