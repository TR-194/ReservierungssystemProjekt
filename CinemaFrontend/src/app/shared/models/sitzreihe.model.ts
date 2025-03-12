import { Sitzplatz } from './sitzplatz.model';
import { Sitzkategorie } from './sitzkategorie.model';

export interface Sitzreihe {
  id: number;
  reihenNummer: number; // Eindeutige Nummer der Sitzreihe
  sitzplaetze: Sitzplatz[];
  kategorie: Sitzkategorie; // Jedem Sitzreihe wird eine Kategorie zugewiesen
}
