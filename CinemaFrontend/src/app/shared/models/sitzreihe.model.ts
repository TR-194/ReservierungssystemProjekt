import { Sitzplatz } from "./sitzplatz.model";

export interface Sitzreihe {
  id: number;
  reihenNummer: number;
  kategorieId: number;  // Referenz zur Kategorie-ID
  sitzplaetze: Sitzplatz[];  // Enthält die Sitzplätze direkt
}