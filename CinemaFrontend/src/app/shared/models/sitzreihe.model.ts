export interface Sitzreihe {
  id: number;
  reihenNummer: number;  // Nummer der Sitzreihe
  kategorieId: number;  // Referenz zur Sitzkategorie
  sitzplatzIds: number[];  // Liste der Sitzplätze in dieser Reihe
  kinosaalId: number; // Zugehöriger Kinosaal
}
